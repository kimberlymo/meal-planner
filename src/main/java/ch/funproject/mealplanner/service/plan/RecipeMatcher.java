package ch.funproject.mealplanner.service.plan;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.Recipe;
import ch.funproject.mealplanner.domain.RecipeIngredient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * RecipeMatcher is an interface that defines methods for finding suitable recipes based on availability and aggregating ingredients for meal planning.
 * It provides a contract for implementations that can match recipes to time constraints and generate shopping lists based on planned meals.
 */
public interface RecipeMatcher {
    /**
     * Finds the best fitting recipe for a given maximum duration.
     *
     * @param maxDuration the maximum duration in minutes for the recipe
     * @return a Mono containing the selected Recipe, or empty if no suitable recipes are found
     */
    Mono<Recipe> findBestFit(long maxDuration);

    /**
     * Aggregates ingredients for a list of meals to create a shopping list.
     *
     * @param mealsToCreate a Flux of Meal objects for which to aggregate ingredients
     * @return a Mono containing a List of RecipeIngredient objects representing the aggregated ingredients
     */
    Mono<List<RecipeIngredient>> receiveIngredientsForMeals(Flux<Meal> mealsToCreate);
}
