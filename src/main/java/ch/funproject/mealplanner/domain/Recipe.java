package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

    @Column(nullable = false)
    private String name;
    private String url;
    @ManyToMany
    private List<RecipeIngredient> ingredients;
    // Duration in minutes
    private int length;
    // Number of portions
    private int portion;
}
