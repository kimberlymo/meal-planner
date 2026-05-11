package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.Ingredient;
import ch.funproject.mealplanner.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service class for managing {@link Ingredient} entities.
 * <p>
 * This service provides a reactive interface for performing CRUD operations on ingredients,
 * wrapping the blocking repository calls into non-blocking Reactor types.
 */
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

    /**
     * Searches for an ingredient by its case-sensitive name.
     *
     * @param name the name of the ingredient to search for.
     * @return a {@link Mono} emitting the matching {@link Ingredient}, or empty if no match is found.
     * @throws IllegalArgumentException if the name is null or blank.
     */
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
