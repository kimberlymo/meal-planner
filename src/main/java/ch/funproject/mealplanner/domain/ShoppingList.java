package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToMany
    private List<Ingredient> ingredients;
    @CreationTimestamp
    private LocalDate creationDate;
}
