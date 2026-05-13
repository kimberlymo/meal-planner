package ch.funproject.mealplanner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MealPlanDto {
    private List<MealDto> meals;
    private ShoppingListDto shoppingList;
}
