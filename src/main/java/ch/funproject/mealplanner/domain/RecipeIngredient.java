package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "receipe_ingredient")
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
    private int amount;

    // was added because of the query that is being used in RecipeIngredientRepository
    protected RecipeIngredient(Ingredient ingredient, long amount) {
        this.ingredient = ingredient;
        this.amount = (int) amount;
    }
}
