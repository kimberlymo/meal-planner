package ch.funproject.mealplanner.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * PlannedMeal domain model.
 * Represents a meal that has been planned with a specific availability/time slot.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlannedMeal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private MealDto meal;
    @ManyToOne
    private AvailabilityDto availability;
}
