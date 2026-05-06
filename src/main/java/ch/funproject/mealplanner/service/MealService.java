package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository repository;
}
