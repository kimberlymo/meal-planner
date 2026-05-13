package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Meal domain model.
 * Represents a meal with its recipe, ingredients, and metadata.
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
    private String url;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RecipeIngredient> ingredients;
    // Duration in minutes
    private int duration;
    // Number of portions
    private int portion;
}
