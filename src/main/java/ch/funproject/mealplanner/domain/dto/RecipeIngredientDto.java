package ch.funproject.mealplanner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RecipeIngredientDto {
    private final String name;
    private final int amount;
}
