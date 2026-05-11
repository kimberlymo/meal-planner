package ch.funproject.mealplanner.domain.mapper;

import ch.funproject.mealplanner.domain.Ingredient;
import ch.funproject.mealplanner.domain.Recipe;
import ch.funproject.mealplanner.domain.RecipeIngredient;
import ch.funproject.mealplanner.domain.dto.RecipeDto;
import ch.funproject.mealplanner.domain.dto.RecipeIngredientDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredients", source = "ingredients", qualifiedByName = "mapIngredients")
    public abstract Recipe toEntity(RecipeDto dto);

    @Named("mapIngredients")
    protected List<RecipeIngredient> mapIngredients(List<RecipeIngredientDto> ingredientsDto) {
        if (ingredientsDto == null) {
            return null;
        }
        return ingredientsDto.stream()
                .map(ingredient -> RecipeIngredient.builder()
                        .amount(ingredient.getAmount())
                        .ingredient(Ingredient.builder().name(ingredient.getName()).build())
                        .build()
                ).toList();
    }
}
