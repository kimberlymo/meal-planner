package ch.funproject.mealplanner.service.plan;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.Recipe;
import ch.funproject.mealplanner.domain.RecipeIngredient;
import ch.funproject.mealplanner.service.RecipeIngredientService;
import ch.funproject.mealplanner.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * RandomRecipeMatcher is a simple implementation of the RecipeMatcher interface.
 * It randomly selects a recipe from the available recipes that fit within the given duration.
 * It also aggregates ingredients for a list of meals to create a shopping list.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RandomRecipeMatcher implements RecipeMatcher {
    public final static int POOL_BUFFER = 3;

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;

    /**
     * Finds the best fitting recipe for a given duration by randomly selecting from a pool of candidates.
     *
     * @param maxDuration the maximum duration in minutes for the recipe
     * @return a Mono containing the selected Recipe, or empty if no suitable recipes are found
     */
    @Override
    public Mono<Recipe> findBestFit(long maxDuration) {
        log.debug("trying to find best fit for availability slot...");
        return recipeService.findByDurationIsLessThanEqual((int) maxDuration)
                .take(POOL_BUFFER)
                .collectList()
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        log.warn("No suitable recipes found for the given duration.");
                        return Mono.empty();
                    }

                    Collections.shuffle(list);
                    Recipe selected = list.getFirst();

                    log.info("Selected recipe '{}' from a pool of {} candidates", selected.getName(), POOL_BUFFER);
                    return Mono.just(selected);
                });
    }

    /**
     * Aggregates ingredients for a list of meals to create a shopping list.
     *
     * @param mealsToCreate a Flux of Meal objects for which to aggregate ingredients
     * @return a Mono containing a List of RecipeIngredient objects representing the aggregated ingredients
     */
    @Override
    public Mono<List<RecipeIngredient>> receiveIngredientsForMeals(Flux<Meal> mealsToCreate) {
        log.info("receiving ingredients for meals to create...");
        return recipeIngredientService.aggregateIngredientsByMeals(mealsToCreate)
                .collectList()
                .doOnSuccess(ingredients -> log.info("Aggregated {} ingredient entries for shopping list", ingredients.size()))
                .doOnError(error -> log.error("Failed to aggregate ingredients: {}", error.getMessage()));
    }
}
