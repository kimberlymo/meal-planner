package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.ShoppingList;
import ch.funproject.mealplanner.service.ShoppingListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Controller for managing shopping lists. Provides endpoints to retrieve and delete shopping lists.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/shopping-lists")
@Tag(name = "Shopping list Management", description = "APIs for managing shopping lists")
public class ShoppingListController {
    private final ShoppingListService service;

    @Operation(summary = "Receives all shopping lists", description = "Receives all shopping lists that are saved")
    @GetMapping
    public Flux<ShoppingList> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Receives a specific shopping list", description = "Receives a specific shopping list with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping list found",
                    content = @Content(schema = @Schema(implementation = ShoppingList.class))),
            @ApiResponse(responseCode = "404", description = "Shopping list not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public Mono<ShoppingList> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("ShoppingList cannot be found with the id {}", id);
                    return new IllegalArgumentException("ShoppingList not found");
                }));

    }

    @Operation(summary = "Receive latest shopping list", description = "Receive last created shopping list")
    @GetMapping("/latest")
    public Mono<ShoppingList> findByLatest() {
        return service.findByLatestCreation();
    }

    @Operation(summary = "Deletes a shopping list", description = "Deletes a shopping list with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping list deleted"),
            @ApiResponse(responseCode = "404", description = "Shopping list not found")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable @NonNull UUID id) {
        return service.deleteById(id);
    }
}
