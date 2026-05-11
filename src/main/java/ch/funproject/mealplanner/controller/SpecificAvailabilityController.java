package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.SpecificAvailability;
import ch.funproject.mealplanner.service.SpecificAvailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Controller for managing specific availabilities. Provides endpoints to create, retrieve, and delete specific availabilities.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/specifics")
@Tag(name = "Specific Availability Management", description = "APIs for managing specific availabilities")
public class SpecificAvailabilityController {
    private final SpecificAvailabilityService service;

    @Operation(summary = "Find all specific availabilities", description = "Retrieve a list of all specific availabilities")
    @GetMapping
    public Flux<SpecificAvailability> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Find a specific availability", description = "Retrieve a specific availability by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specific availability found"),
            @ApiResponse(responseCode = "404", description = "Specific availability not found")
    })
    @GetMapping("/{id}")
    public Mono<SpecificAvailability> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("did not find entry with uuid: {}", id);
                    return new IllegalArgumentException("RecurringAvailability not found");
                }));
    }

    @Operation(summary = "Find specific availabilities by date range", description = "Retrieve a list of specific availabilities that fall within the specified start and end dates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specific availabilities found"),
            @ApiResponse(responseCode = "400", description = "Invalid date format")
    })
    @GetMapping("/range/{start}/{end}")
    public Flux<SpecificAvailability> findByDateRange(@PathVariable @NonNull String start, @PathVariable @NonNull String end) {
        return service.findByDateRange(LocalDate.parse(start), LocalDate.parse(end));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a specific availability", description = "Delete a specific availability by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specific availability deleted"),
            @ApiResponse(responseCode = "404", description = "Specific availability not found")
    })
    public Mono<Void> deleteById(@PathVariable @NonNull UUID id) {
        return service.deleteById(id);
    }

    @Operation(summary = "Create a specific availability", description = "Create a new specific availability with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Specific availability saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Mono<SpecificAvailability> save(@RequestBody @NonNull SpecificAvailability availability) {
        return service.save(availability.getDate(), availability.getStartTime(), availability.getEndTime(), true);
    }
}

