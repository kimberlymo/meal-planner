package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.RecipeIngredient;
import ch.funproject.mealplanner.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RecipeIngredientService {
    private RecipeIngredientRepository repository;

    public Flux<RecipeIngredient> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<RecipeIngredient> findById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Mono<RecipeIngredient> findByAmountAndName(int amount, String name) {
        if (amount <= 0 || name == null || name.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Cannot get RecipeIngredient with invalid data"));
        }

        return Mono.fromCallable(() -> repository.findByAmountAndIngredient_Name(amount, name))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Mono<RecipeIngredient> save(RecipeIngredient toSave) {
        if (toSave == null || toSave.getAmount() < 0 || toSave.getIngredient() == null) {
            return Mono.error(new IllegalArgumentException("Recipe Ingredient must contain valid data"));
        }
        return Mono.fromCallable(() -> repository.save(toSave));
    }


    public Mono<Void> deleteById(UUID id) {
        return findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Cannot find RecipeIngredient")))
                .then(Mono.fromRunnable(() -> repository.deleteById(id)));
    }
}
