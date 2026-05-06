package ch.funproject.mealplanner.domain.dto;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Availability sealed base class.
 * Represents time slots when a meal can be prepared.
 * Two implementations: RepeatingAvailability (weekly) and SpecificAvailability (one-time).
 */
@Getter
public abstract class AvailabilityDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
