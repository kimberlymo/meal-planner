package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * @param amount the numeric quantity of the ingredient.
     * @param ingredient_name the name of the ingredient (traversed from the Ingredient entity).
     * @return an {@link Optional} containing the matching {@link RecipeIngredient},
     *         or empty if no match is found.
     */
    Optional<RecipeIngredient> findByAmountAndIngredient_Name(int amount, String ingredient_name);
}
