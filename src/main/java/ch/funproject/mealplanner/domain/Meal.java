package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * PlannedMeal domain model.
 * Represents a meal that has been planned with a specific availability/time slot.
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Recipe recipe;
    @Column(name = "planned_date")
    private LocalDate plannedDate;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
}
