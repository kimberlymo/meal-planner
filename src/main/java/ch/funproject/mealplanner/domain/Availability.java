package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Availability sealed base class.
 * Represents time slots when a meal can be prepared.
 * Two implementations: RepeatingAvailability (weekly) and SpecificAvailability (one-time).
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter
public abstract class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
