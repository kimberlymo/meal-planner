package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.repository.PlannedMealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlannedMealService {
    private final PlannedMealRepository repository;
}
