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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    @Column(unique=true)
    private String name;
}