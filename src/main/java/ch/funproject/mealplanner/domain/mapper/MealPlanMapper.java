package ch.funproject.mealplanner.domain.mapper;

import ch.funproject.mealplanner.domain.*;
import ch.funproject.mealplanner.domain.dto.MealDto;
import ch.funproject.mealplanner.domain.dto.MealPlanDto;
import ch.funproject.mealplanner.domain.dto.RecipeIngredientDto;
import ch.funproject.mealplanner.domain.dto.ShoppingListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UnitMapper.class })
public interface MealPlanMapper {

    // MapStruct sees List<Meal> and List<MealDto>, then looks for a
    // Meal -> MealDto mapper to use for each element automatically.
    MealPlanDto toDto(MealPlan mealPlan);

    @Mapping(target = "recipeName", source = "recipe.name")
    @Mapping(target = "recipeUrl", source = "recipe.url")
    MealDto toMealDto(Meal meal);

    ShoppingListDto toShoppingListDto(ShoppingList shoppingList);

    @Mapping(target = "unit", source = "recipeIngredient.unit")
    @Mapping(target = "name", source = "ingredient.name")
    RecipeIngredientDto toRecipeIngredient(RecipeIngredient recipeIngredient);

}
