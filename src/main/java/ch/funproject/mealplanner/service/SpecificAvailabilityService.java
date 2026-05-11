package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.SpecificAvailability;
import ch.funproject.mealplanner.repository.SpecificAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Service class for managing SpecificAvailability entities. Provides methods to find, save, and delete specific availabilities.
 * This service uses reactive programming with Project Reactor to handle asynchronous operations.
 */
@Service
@RequiredArgsConstructor
public class SpecificAvailabilityService {
    private final SpecificAvailabilityRepository repository;

    public Flux<SpecificAvailability> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<SpecificAvailability> findById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Flux<SpecificAvailability> findByDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return Flux.error(new IllegalArgumentException("Start date and end date cannot be null"));
        }
        if (startDate.isAfter(endDate)) {
            return Flux.error(new IllegalArgumentException("Start date cannot be after end date"));
        }

        return Flux.fromIterable(repository.findByDateBetween(startDate, endDate));
    }

    public Mono<SpecificAvailability> save(LocalDate date, LocalTime startTime, LocalTime endTime, boolean isAvailable) {
        if (date == null || startTime == null || endTime == null) {
            return Mono.error(new IllegalArgumentException("Date or time cannot be null"));
        }

        if (startTime.isAfter(endTime)) {
            return Mono.error(new IllegalArgumentException("Start Time cannot be after end time"));
        }

        return Mono.fromCallable(() -> repository.save(SpecificAvailability.builder()
                        .date(date)
                        .startTime(startTime)
                        .endTime(endTime)
                        .isAvailable(isAvailable)
                .build()));
    }

    public Mono<Void> deleteById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }

        return findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the availability with the given ID")))
                .then(Mono.fromRunnable(() -> repository.deleteById(id)));
    }
}
