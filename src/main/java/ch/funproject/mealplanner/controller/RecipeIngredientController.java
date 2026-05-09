package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.RecipeIngredient;
import ch.funproject.mealplanner.service.RecipeIngredientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recipe-ingredients")
public class RecipeIngredientController {
    private final RecipeIngredientService service;

    @GetMapping
    public Flux<RecipeIngredient> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<RecipeIngredient> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("RecipeIngredient cannot be found with the id {}", id);
                    return new IllegalArgumentException("RecipeIngredient not found");
                }));

    }

    @GetMapping
    public Mono<RecipeIngredient> findByAmountAndName(@RequestParam int amount, @RequestParam @NonNull String name) {
        return service.findByAmountAndName(amount, name);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable @NonNull UUID id) {
        return service.deleteById(id);
    }
}
