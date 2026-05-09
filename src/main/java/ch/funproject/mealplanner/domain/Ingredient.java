package ch.funproject.mealplanner.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Ingredient domain model.
 * Represents a single ingredient that can be used in meals.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique=true)
    private String name;

}