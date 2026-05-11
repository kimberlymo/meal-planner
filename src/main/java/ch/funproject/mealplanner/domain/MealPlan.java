package ch.funproject.mealplanner.domain;

import java.util.List;

public record MealPlan(List<Meal> meals, ShoppingList shoppingList) {
}
