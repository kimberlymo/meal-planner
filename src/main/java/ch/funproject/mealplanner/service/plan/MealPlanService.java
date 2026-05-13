package ch.funproject.mealplanner.service.plan;

import ch.funproject.mealplanner.domain.*;
import ch.funproject.mealplanner.domain.dto.MealPlanDto;
import ch.funproject.mealplanner.domain.mapper.MealPlanMapper;
import ch.funproject.mealplanner.service.MealService;
import ch.funproject.mealplanner.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * MealPlanService is responsible for generating meal plans based on user availability and recipes.
 * It interacts with the MealService to create meals, the ShoppingListService to create shopping lists,
 * and the AvailabilityService to get user availability. It also uses a RecipeMatcher to find suitable recipes.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MealPlanService {
    private final static int CLEANING_TIME_BUFFER = 15;

    private final MealService mealService;
    private final ShoppingListService shoppingListService;
    private final AvailabilityService availabilityService;
    private final RecipeMatcher recipeMatcher;

    private final MealPlanMapper mealPlanMapper;

    /**
     * Generates a meal plan for the given date range.
     * It retrieves user availability, finds suitable recipes, creates meals, and generates a shopping list.
     *
     * @param start the start of the date range for the meal plan
     * @param end   the end of the date range for the meal plan
     * @return a Mono containing the generated MealPlan
     */
    public Mono<MealPlanDto> generatePlan(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            log.error("Invalid date range provided: start {} is after end {}", start, end);
            return Mono.error(new IllegalArgumentException("Start cannot be after End for range"));
        }

        log.info("Generating new meal Plan from {} until {}", start, end);
        Flux<Meal> mealsToCreate = availabilityService.getAllAvailabilitiesInRange(start, end)
                .flatMap(slot -> {
                    long durationInMinutes = Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes() - CLEANING_TIME_BUFFER;
                    return recipeMatcher.findBestFit(durationInMinutes)
                            .map(recipe -> buildMeal(slot, recipe, durationInMinutes));
                }).flatMap(mealService::save);

        Mono<ShoppingList> shoppingList = createShoppingListFromMeals(mealsToCreate);

        return Mono.zip(mealsToCreate.collectList(), shoppingList)
                .map(tuple -> new MealPlan(tuple.getT1(), tuple.getT2()))
                .map(mealPlanMapper::toDto)
                .doOnSuccess(plan -> log.info("Successfully generated meal plan with {} meals", plan.getMeals().size()))
                .doOnError(error -> log.error("Failed to generate meal plan: {}", error.getMessage()));
    }

    private Mono<ShoppingList> createShoppingListFromMeals(Flux<Meal> mealsToCreate) {
        log.debug("Aggregating ingredients for shopping list...");
        return recipeMatcher.receiveIngredientsForMeals(mealsToCreate)
                .flatMap(list -> {
                    log.debug("Saving shopping list with {} ingredient entries", list.size());
                    return shoppingListService.save(ShoppingList.builder().ingredients(list).build());
                });
    }

    private Meal buildMeal(SpecificAvailability slot, Recipe recipe, long durationInMinutes) {
        return Meal.builder()
                .recipe(recipe)
                .plannedDate(slot.getDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getStartTime().plusMinutes(durationInMinutes))
                .build();
    }
}
