package ch.funproject.mealplanner.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * SpecificAvailability domain model.
 * Represents a one-time availability slot with a specific date and time.
 */
@Entity
@DiscriminatorValue("SPECIFIC")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class SpecificAvailability extends Availability {
    private LocalDateTime startDateTime;    // Start date and time
    private LocalDateTime endDateTime;      // End date and time
}
