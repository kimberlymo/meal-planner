package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.RecurringAvailability;
import ch.funproject.mealplanner.service.RecurringAvailabilityService;
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
 * Controller for managing recurring availabilities. Provides endpoints to create, retrieve, and delete recurring availabilities.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recurrings")
@Tag(name = "Recurring Availability Management", description = "APIs for managing recurring availabilities")
public class RecurringAvailabilityController {
    private final RecurringAvailabilityService service;

    @Operation(summary = "Get all recurring availabilities", description = "Retrieve a list of all recurring availabilities")
    @GetMapping
    public Flux<RecurringAvailability> findAll() {
        return service.findAll();
    }

    @Operation(summary = "Get a specific recurring availability", description = "Retrieve a specific recurring availability by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurring availability found"),
            @ApiResponse(responseCode = "404", description = "Recurring availability not found")
    })
    @GetMapping("/{id}")
    public Mono<RecurringAvailability> findById(@PathVariable @NonNull UUID id) {
        return service.findById(id)
                .switchIfEmpty(Mono.error(() -> {
                    log.warn("did not find entry with uuid: {}", id);
                    return new IllegalArgumentException("RecurringAvailability not found");
                }));
    }

    @Operation(summary = "Create or update a recurring availability", description = "Create a new recurring availability or update an existing one with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recurring availability saved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public Mono<RecurringAvailability> save(@RequestBody @NonNull RecurringAvailability availability) {
        return service.save(availability);
    }

    @Operation(summary = "Delete a recurring availability series", description = "Delete an entire series of recurring availability with a given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Series deleted"),
            @ApiResponse(responseCode = "404", description = "Recurring availability not found")
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteSeries(@PathVariable @NonNull UUID id) {
        return service.deleteSeries(id);
    }

    @Operation(summary = "Delete a single entry of a recurring availability", description = "Delete a single entry of a recurring availability with a given ID and date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Single entry deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid date parameter"),
            @ApiResponse(responseCode = "404", description = "Recurring availability not found")
    })
    @DeleteMapping("/{id}/single")
    public Mono<Void> deleteSingle(@PathVariable @NonNull UUID id, @RequestParam("date") String date) {
        if (date == null || date.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Date parameter is required"));
        }

        return service.deleteSingleEntry(id, LocalDate.parse(date));
    }
}

