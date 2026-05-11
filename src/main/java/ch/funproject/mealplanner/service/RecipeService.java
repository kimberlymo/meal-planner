package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.Recipe;
import ch.funproject.mealplanner.domain.dto.RecipeDto;
import ch.funproject.mealplanner.domain.mapper.RecipeMapper;
import ch.funproject.mealplanner.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository repository;
    private final RecipeIngredientService recipeIngredientService;
    private final RecipeMapper mapper;

    public Flux<Recipe> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<Recipe> findById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Flux<Recipe> findByDurationIsLessThanEqual(int maxDuration) {
        if (maxDuration <= 1) {
            return Flux.error(new IllegalArgumentException("The max duration cannot be less than one minute"));
        }

        return Flux.fromIterable(repository.findByDurationIsLessThanEqualOrderByDurationDesc(maxDuration));
    }

    public Mono<Recipe> save(RecipeDto recipe) {
        if (recipe == null) {
            return Mono.error(new IllegalArgumentException("Meal cannot be null"));
        }
        if (recipe.getName() == null || recipe.getName().isBlank()) {
            return Mono.error(new IllegalArgumentException("Meal name cannot be empty"));
        }

        if (recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Meal needs ingredients!"));
        }

        return Flux.fromIterable(recipe.getIngredients())
                .flatMap(ingredient -> recipeIngredientService.findByAmountAndName(ingredient.getAmount(), ingredient.getName())
                        .switchIfEmpty(recipeIngredientService.save(ingredient)))
                .then(Mono.fromCallable(() -> repository.save(mapper.toEntity(recipe))));
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
