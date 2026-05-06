package ch.funproject.mealplanner.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * RepeatingAvailability domain model.
 * Represents a recurring time slot (e.g., every Monday 18:00-20:00).
 */
@Entity
@DiscriminatorValue("REPEATING")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class RepeatingAvailability extends Availability {
    private DayOfWeek dayOfWeek;      // Day of the week
    private LocalTime startTime;      // Start time
    private LocalTime endTime;        // End time
}
