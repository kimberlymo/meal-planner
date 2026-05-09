package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.ShoppingList;
import ch.funproject.mealplanner.service.ShoppingListService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shopping-lists")
public class ShoppingListController {
    private final ShoppingListService service;

    @GetMapping
    public Flux<ShoppingList> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ShoppingList> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("ShoppingList cannot be found with the id {}", id);
                    return new IllegalArgumentException("ShoppingList not found");
                }));

    }

    @GetMapping("/latest")
    public Mono<ShoppingList> findByLatest() {
        return service.findByLatestCreation();
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable @NonNull UUID id) {
        return service.deleteById(id);
    }
}
