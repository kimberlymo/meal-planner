package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * ShoppingList domain model.
 * Represents a shopping list containing ingredients to be purchased.
 */
@Entity(name = "shopping_list")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany
    private List<RecipeIngredient> ingredients;
    @CreationTimestamp
    private LocalDate creationDate;
}
