package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.Ingredient;
import ch.funproject.mealplanner.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;

    public Flux<Ingredient> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<Ingredient> findById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Mono<Ingredient> findByName(String name) {
        if (name == null || name.isBlank()) {
            return Mono.error(new IllegalArgumentException("Name cannot be empty for ingredient"));
        }
        return Mono.fromCallable(() -> repository.findIngredientByName(name))
                .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty));
    }

    public Mono<Ingredient> save(String name) {
        if (name == null || name.isBlank()) {
            return Mono.error(new IllegalArgumentException("Name cannot be empty"));
        }
        Ingredient toSave = Ingredient.builder().name(name).build();
        return Mono.fromCallable(() -> repository.save(toSave));
    }

    public Mono<Void> deleteById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }

        return findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the ingredient with the given ID")))
                .then(Mono.fromRunnable(() -> repository.deleteById(id)));
    }
}
