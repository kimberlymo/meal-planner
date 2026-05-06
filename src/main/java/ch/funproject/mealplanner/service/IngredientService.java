package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;
}
