package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link RecipeIngredient} entities.
 * <p>
 * This interface leverages Spring Data JPA to provide standard asynchronous-capable
 * CRUD operations against the persistence layer.
 */
@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
    /**
     * Retrieves a recipe-ingredient link based on a specific quantity and the
     * associated ingredient's name.
     *
     * @param amount          the numeric quantity of the ingredient.
     * @param ingredient_name the name of the ingredient (traversed from the Ingredient entity).
     * @return an {@link Optional} containing the matching {@link RecipeIngredient},
     * or empty if no match is found.
     */
    Optional<RecipeIngredient> findByAmountAndIngredient_Name(int amount, String ingredient_name);

    @Query("""
                SELECT new ch.funproject.mealplanner.domain.RecipeIngredient(ri.ingredient, SUM(ri.amount))
                FROM Meal m
                JOIN m.recipe r
                JOIN r.ingredients ri
                WHERE m IN :meals
                GROUP BY ri.ingredient
            """)
    List<RecipeIngredient> aggregateIngredientsByMeals(@Param("meals") List<Meal> meals);
}
