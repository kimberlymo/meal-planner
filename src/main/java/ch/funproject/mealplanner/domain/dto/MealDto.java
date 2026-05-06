package ch.funproject.mealplanner.domain.dto;

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
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String url;
    @ManyToMany
    private List<IngredientDto> ingredients;
    private int length;        // Duration in minutes
    private int portion;       // Number of portions
    private LocalDate lastCooked;
}
