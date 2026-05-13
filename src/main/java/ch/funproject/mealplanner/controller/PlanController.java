package ch.funproject.mealplanner.controller;

import ch.funproject.mealplanner.domain.dto.MealPlanDto;
import ch.funproject.mealplanner.service.plan.MealPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Controller for managing ingredients. Provides endpoints to create, retrieve, and delete ingredients.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/plan")
@Tag(name = "Automatic plan Management", description = "APIs for creating plans for meals")
public class PlanController {
    private final MealPlanService mealPlanService;

    @Operation(summary = "Generate a meal plan for a given time range", description = "Generates a meal plan based on the provided start and end times. NOTE: PLEASE ENTER RANGES IN FORMAT YYYY-MM-DD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meal plan generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
    })
    @PostMapping("/generate/{start}/{end}")
    public Mono<MealPlanDto> generateMealPlan(@PathVariable @NonNull String start, @PathVariable @NonNull String end) {
        if (start.isBlank() || end.isBlank()) {
            return Mono.error(new IllegalArgumentException("Start and end times must not be blank"));
        }

        var startTime = LocalDate.parse(start);
        var endTime = LocalDate.parse(end);
        return mealPlanService.generatePlan(LocalDateTime.of(startTime, LocalTime.MIN), LocalDateTime.of(endTime, LocalTime.MIDNIGHT));
    }
}
