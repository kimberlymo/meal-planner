package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.Recipe;
import ch.funproject.mealplanner.domain.dto.RecipeDto;
import ch.funproject.mealplanner.service.RecipeService;
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
 * Controller for managing recipes. Provides endpoints to create, retrieve, and delete recipes.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
@Tag(name = "Recipe Management", description = "APIs for managing all recipes")
public class RecipeController {
    private final RecipeService service;

    @Operation(summary = "Receives all recipes", description = "Receives all recipes that are possible to plan")
    @GetMapping
    public Flux<Recipe> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Receives a specific recipe", description = "Receives a specific recipe with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "404", description = "Recipe not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public Mono<Recipe> findById(@PathVariable UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("did not find entry with uuid: {}", id);
                    return new IllegalArgumentException("Recipe not found");
                }));
    }

    @Operation(summary = "Deletes a recipe", description = "Deletes a recipe with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe deleted"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable UUID id) {
        return service.deleteById(id);
    }

    @Operation(summary = "Creates a new recipe", description = "Creates a new recipe as well as ingredients if needed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Recipe created",
                    content = @Content(schema = @Schema(implementation = Recipe.class))),
            @ApiResponse(responseCode = "400", description = "Recipe is invalid")
    })
    @PostMapping
    public Mono<Recipe> save(@RequestBody @NonNull RecipeDto recipe) {
        return service.save(recipe);
    }
}
