package ch.funproject.mealplanner.domain.mapper;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.dto.MealDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MealMapper {

    @Mapping(target = "recipeName", source = "recipe.name")
    @Mapping(target = "recipeUrl", source = "recipe.url")
    MealDto toDto(Meal entity);

}
