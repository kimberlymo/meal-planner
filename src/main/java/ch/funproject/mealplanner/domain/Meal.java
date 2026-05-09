package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * PlannedMeal domain model.
 * Represents a meal that has been planned with a specific availability/time slot.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Recipe recipe;
    @ManyToOne
    private Availability availability;
}
