package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingListService {
    private final ShoppingListRepository repository;
}
