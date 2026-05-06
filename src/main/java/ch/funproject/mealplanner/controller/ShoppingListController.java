package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.service.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/shopping-list")
public class ShoppingListController {
    private final ShoppingListService service;
}
