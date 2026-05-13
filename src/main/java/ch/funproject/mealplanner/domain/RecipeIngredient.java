package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "recipe_ingredient")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Ingredient ingredient;
    @Min(0)
    @Max(10_000)
    private int amount;
    private Unit unit;

    // was added because of the query that is being used in RecipeIngredientRepository
    protected RecipeIngredient(Ingredient ingredient, long amount) {
        this.ingredient = ingredient;
        this.amount = (int) amount;
    }
}
