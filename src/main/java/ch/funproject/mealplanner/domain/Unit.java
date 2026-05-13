package ch.funproject.mealplanner.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum representing the unit of measurement for ingredients.
 */
public enum Unit {
    @JsonProperty("kg") KG,
    @JsonProperty("gram")GRAMM,
    @JsonProperty("liter")LITER,
    @JsonProperty("dl")DECILITER,
    @JsonProperty("ml")MILLILITER,
    @JsonProperty("piece")PIECE
}
