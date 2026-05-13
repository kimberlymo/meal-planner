package ch.funproject.mealplanner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    private String name;
    private String url;
    private List<RecipeIngredientDto> ingredients;
    private int duration;        // Duration in minutes
    private int portion;       // Number of portions
    private LocalDate lastCooked;
}
