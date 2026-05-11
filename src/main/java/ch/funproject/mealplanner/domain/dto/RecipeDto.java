package ch.funproject.mealplanner.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class RecipeDto {
    private String name;
    private String url;
    private List<RecipeIngredientDto> ingredients;
    private int length;        // Duration in minutes
    private int portion;       // Number of portions
    private LocalDate lastCooked;
}
