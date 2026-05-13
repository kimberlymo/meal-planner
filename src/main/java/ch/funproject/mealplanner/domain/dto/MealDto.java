package ch.funproject.mealplanner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
    private String recipeName;
    private String recipeUrl;
    private LocalDate plannedDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
