package ch.funproject.mealplanner.domain.mapper;

import ch.funproject.mealplanner.domain.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    @ValueMapping(source = "kg", target = "KG")
    @ValueMapping(source = "gram", target = "GRAMM")
    @ValueMapping(source = "liter", target = "LITER")
    @ValueMapping(source = "dl", target = "DECILITER")
    @ValueMapping(source = "ml", target = "MILLILITER")
    @ValueMapping(source = "pieces", target = "PIECES")
    @ValueMapping(source = "Tbsp", target = "TABLESPOON")
    @ValueMapping(source = "cup", target = "CUP")
    @ValueMapping(source = "tsp", target = "TEASPOON")
    @ValueMapping(source = "pinch", target = "PINCH")
    // This handles any string that doesn't match the list above
    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = MappingConstants.NULL)
    Unit stringToUnit(String unitString);
}