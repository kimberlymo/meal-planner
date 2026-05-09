package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, UUID> {
    Optional<RecipeIngredient> findByAmountAndIngredient_Name(int amount, String ingredient_name);
}
