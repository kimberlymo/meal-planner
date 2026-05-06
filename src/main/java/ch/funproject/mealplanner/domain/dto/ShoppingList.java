package ch.funproject.mealplanner.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * ShoppingList domain model.
 * Represents a shopping list containing ingredients to be purchased.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany
    private List<IngredientDto> ingredients;
    private LocalDate creationDate;
}
