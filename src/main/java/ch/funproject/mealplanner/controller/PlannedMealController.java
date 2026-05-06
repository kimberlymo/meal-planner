package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.service.PlannedMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("planned-meal")
public class PlannedMealController {
    private final PlannedMealService service;
}
