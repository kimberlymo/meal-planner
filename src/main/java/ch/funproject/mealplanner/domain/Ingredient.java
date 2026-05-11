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
    @Column(unique=true)
    private String name;
}