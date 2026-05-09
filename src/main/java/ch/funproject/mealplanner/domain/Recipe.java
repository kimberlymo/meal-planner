package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Meal domain model.
 * Represents a meal with its recipe, ingredients, and metadata.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String url;
    @ManyToMany
    private List<RecipeIngredient> ingredients;
    private int length;        // Duration in minutes
    private int portion;       // Number of portions
    private LocalDate lastCooked;
}
