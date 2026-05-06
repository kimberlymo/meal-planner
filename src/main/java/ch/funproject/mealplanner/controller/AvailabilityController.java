package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/availability")
public class AvailabilityController {
    private final AvailabilityService service;
}

