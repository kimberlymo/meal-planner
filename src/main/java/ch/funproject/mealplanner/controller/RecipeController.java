package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.Recipe;
import ch.funproject.mealplanner.service.RecipeService;
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
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService service;

    @GetMapping
    public Flux<Recipe> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Recipe> findById(@PathVariable UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("did not find entry with uuid: {}", id);
                    return new IllegalArgumentException("Recipe not found");
                }));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable UUID id) {
        return service.deleteById(id);
    }

    @PostMapping
    public Mono<Recipe> save(@RequestBody @NonNull Recipe recipe) {
        return service.save(recipe);
    }
}
