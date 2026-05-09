package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.Ingredient;
import ch.funproject.mealplanner.service.IngredientService;
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
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService service;

    @GetMapping
    public Flux<Ingredient> findAll() {
        return service.findAll();
    }

    @GetMapping("/{uuid}")
    public Mono<Ingredient> findById(@PathVariable @NonNull UUID uuid) {
        return service.findById(uuid)
                .doOnNext(ingredient -> log.warn("did not find entry with uuid: {}", uuid))
                .switchIfEmpty(Mono.error(() -> new IllegalArgumentException("Ingredient not found")));
    }

    @DeleteMapping("/{uuid}")
    public Mono<Void> deleteById(@PathVariable @NonNull UUID uuid) {
        return service.deleteById(uuid);
    }

    @PostMapping
    public Mono<Ingredient> save(@RequestBody @NonNull Ingredient ingredient) {
        return service.save(ingredient.getName());
    }
}
