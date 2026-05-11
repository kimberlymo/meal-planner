package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Service class for managing {@link Meal} entities.
 * <p>
 * This service handles the business logic for scheduling meals, including
 * validation of meal timings and persistence of meal planning data.
 */
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

    /**
     * Validates and persists a meal plan entry.
     * <p>
     * Validation checks performed:
     * <ul>
     *     <li>Meal and associated Recipe must not be null.</li>
     *     <li>Start and End times must be provided.</li>
     *     <li>The Start time must occur before the End time.</li>
     * </ul>
     *
     * @param meal the {@link Meal} entity to save.
     * @return a {@link Mono} emitting the persisted {@link Meal}.
     * @throws IllegalArgumentException if any validation criteria are not met.
     */
    public Mono<Meal> save(Meal meal) {
        if (meal == null) {
            return Mono.error(new IllegalArgumentException("Meal cannot be null"));
        }
        if (meal.getRecipe() == null) {
            return Mono.error(new IllegalArgumentException("Meal cannot be null"));
        }
        if (meal.getStartTime() == null || meal.getEndTime() == null) {
            return Mono.error(new IllegalArgumentException("Start time and end time cannot be null"));
        }

        if (meal.getStartTime().isAfter(meal.getEndTime())) {
            return Mono.error(new IllegalArgumentException("Start time cannot be after end time"));
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
