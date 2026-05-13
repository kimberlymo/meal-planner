package ch.funproject.mealplanner.domain.dto;

import ch.funproject.mealplanner.domain.Unit;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeIngredientDto {
    private String name;
    private int amount;
    private Unit unit;
}
