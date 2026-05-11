package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.RecipeIngredient;
import ch.funproject.mealplanner.service.RecipeIngredientService;
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
 * Controller for managing recipe ingredients. Provides endpoints to retrieve and delete recipe ingredients.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recipe-ingredients")
@Tag(name = "Recipe Ingredient Management", description = "APIs for managing all ingredients that are linked to one or more recipes")
public class RecipeIngredientController {
    private final RecipeIngredientService service;

    @Operation(summary = "Receive all recipe ingredients", description = "Receives all ingredients that are linked to a recipe")
    @GetMapping
    public Flux<RecipeIngredient> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Receives a specific recipe ingredients", description = "Receives a specific recipe ingredients with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RecipeIngredient found",
                    content = @Content(schema = @Schema(implementation = RecipeIngredient.class))),
            @ApiResponse(responseCode = "404", description = "RecipeIngredient not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public Mono<RecipeIngredient> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("RecipeIngredient cannot be found with the id {}", id);
                    return new IllegalArgumentException("RecipeIngredient not found");
                }));

    }

    @Operation(summary = "Receives a ingredient with a given name and amount", description = "Receives a SINGLE ingredient with a given name and amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RecipeIngredient found",
                    content = @Content(schema = @Schema(implementation = RecipeIngredient.class))),
            @ApiResponse(responseCode = "404", description = "RecipeIngredient not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/ingredient")
    public Mono<RecipeIngredient> findByAmountAndName(@RequestParam int amount, @RequestParam @NonNull String name) {
        return service.findByAmountAndName(amount, name);
    }

    @Operation(summary = "Deletes a recipe ingredient", description = "Removes a recipe ingredient with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "RecipeIngredient deleted"),
            @ApiResponse(responseCode = "404", description = "RecipeIngredient not found")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable @NonNull UUID id) {
        return service.deleteById(id);
    }
}
