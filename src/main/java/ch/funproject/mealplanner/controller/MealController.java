package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/meal")
public class MealController {
    private final MealService service;
}
