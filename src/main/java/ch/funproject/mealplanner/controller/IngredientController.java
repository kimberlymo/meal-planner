package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.Ingredient;
import ch.funproject.mealplanner.service.IngredientService;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredients")
@Tag(name = "Ingredient Management", description = "APIs for managing ingredients")
public class IngredientController {
    private final IngredientService service;

    @Operation(summary = "Get all ingredients", description = "Receives all the ingredients that are saved")
    @ApiResponse(responseCode = "200", description = "All Ingredients",
            content = @Content(schema = @Schema(implementation = Ingredient.class)))
    @GetMapping
    public Flux<Ingredient> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get ingredient by id", description = "Receives an ingredient depending on their id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient found",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "404", description = "Ingredient not found",
                    content = @Content(schema = @Schema()))
    })
    @GetMapping("/{uuid}")
    public Mono<Ingredient> findById(@PathVariable @NonNull UUID uuid) {
        return service.findById(uuid)
                .doOnNext(ingredient -> log.warn("did not find entry with uuid: {}", uuid))
                .switchIfEmpty(Mono.error(() -> new IllegalArgumentException("Ingredient not found")));
    }

    @Operation(summary = "deletes an ingredient", description = "deletes an ingredient that is not used anymore")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient deleted"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    @DeleteMapping("/{uuid}")
    public Mono<Void> deleteById(@PathVariable @NonNull UUID uuid) {
        return service.deleteById(uuid);
    }

    @Operation(summary = "Creates an ingredient", description = "Creates a new ingredient that can be later on be used for recipes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Ingredient created",
                    content = @Content(schema = @Schema(implementation = Ingredient.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Ingredient",
                    content = @Content(schema = @Schema()))
    })
    @PostMapping
    public Mono<Ingredient> save(@RequestBody @NonNull Ingredient ingredient) {
        return service.save(ingredient.getName());
    }
}
