package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.service.MealService;
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
 * Controller for managing meals. Provides endpoints to create, retrieve, and delete meals.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/meals")
@Tag(name = "Meal Management", description = "APIs for managing all meals")
public class MealController {
    private final MealService service;

    @Operation(summary = "Receive all meals", description = "Receives all possible and planned meals")
    @GetMapping
    public Flux<Meal> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Receive specific meal", description = "Receives a meal with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal found",
                    content = @Content(schema = @Schema(implementation = Meal.class))),
            @ApiResponse(responseCode = "404", description = "Meal not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{id}")
    public Mono<Meal> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("Meal cannot be found with the id {}", id);
                    return new IllegalArgumentException("Meal not found");
                }));
    }

    @Operation(summary = "Create new meal", description = "Creates a new meal with a given time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Meal created",
                    content = @Content(schema = @Schema(implementation = Meal.class))),
            @ApiResponse(responseCode = "400", description = "Meal is invalid")
    })
    @PostMapping
    public Mono<Meal> save(@RequestBody @NonNull Meal toSave) {
        return service.save(toSave);
    }

    @Operation(summary = "Deletes a meal", description = "Removes a planned meal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal deleted"),
            @ApiResponse(responseCode = "404", description = "Meal not found")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable UUID id) {
        return service.deleteById(id);
    }
}
