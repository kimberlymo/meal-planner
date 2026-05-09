package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository repository;

    public Flux<Meal> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<Meal> findById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }

        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Mono<Meal> save(Meal meal) {
        if (meal == null) {
            return Mono.error(new IllegalArgumentException("Meal cannot be null"));
        }
        if (meal.getRecipe() == null) {
            return Mono.error(new IllegalArgumentException("Meal cannot be null"));
        }
        if (meal.getAvailability() == null) {
            return Mono.error(new IllegalArgumentException("Availability cannot be null"));
        }

        return Mono.fromCallable(() -> repository.save(meal));
    }

    public Mono<Void> deleteById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }

        return findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the meal with the given ID")))
                .then(Mono.fromRunnable(() -> repository.deleteById(id)));
    }
}
