package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.ShoppingList;
import ch.funproject.mealplanner.repository.ShoppingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingListService {
    private final ShoppingListRepository repository;

    public Flux<ShoppingList> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<ShoppingList> findById(UUID id) {
        if (id == null) {
            Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Mono<ShoppingList> findByLatestCreation() {
        return Mono.fromCallable(repository::findFirstByOrderByCreationDateDesc);
    }

    public Mono<ShoppingList> save(ShoppingList shoppingList) {
        if (shoppingList == null) {
            throw new IllegalArgumentException("ShoppingList cannot be null");
        }
        if (shoppingList.getIngredients() == null || shoppingList.getIngredients().isEmpty()) {
            throw new IllegalArgumentException("ShoppingList must contain at least one ingredient");
        }
        return Mono.fromCallable(() -> repository.save(shoppingList));
    }

    public Mono<Void> deleteById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Cannot find the meal with the given ID")))
                .then(Mono.fromRunnable(() -> repository.deleteById(id)));
    }
}
