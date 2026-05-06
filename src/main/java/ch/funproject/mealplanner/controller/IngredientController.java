package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/ingredient")
public class IngredientController {
    private final IngredientService service;
}
