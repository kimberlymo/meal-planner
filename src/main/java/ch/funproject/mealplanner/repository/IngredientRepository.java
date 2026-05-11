package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link Ingredient} entities.
 * <p>
 * Provides standard CRUD functionality by extending {@link JpaRepository}.
 * This interface handles the abstraction of SQL queries for the underlying database
 * while managing the persistence of ingredient data.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    /**
     * Finds a single ingredient by its name using Spring Data JPA's query derivation.
     * <p>
     * Note: This search is case-sensitive by default unless specified otherwise
     * in the entity configuration or query method name.
     *
     * @param name the exact string name of the ingredient to search for.
     * @return an {@link Optional} containing the found {@link Ingredient},
     *         or {@link Optional#empty()} if no match exists.
     */
    Optional<Ingredient> findIngredientByName(String name);
}
