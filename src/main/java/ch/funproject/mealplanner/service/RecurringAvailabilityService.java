package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.RecurringAvailability;
import ch.funproject.mealplanner.domain.SpecificAvailability;
import ch.funproject.mealplanner.repository.RecurringAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Service class for managing RecurringAvailability entities. Provides methods to find, save, and delete recurring availabilities.
 * This service uses reactive programming with Project Reactor to handle asynchronous operations.
 */
@Service
@RequiredArgsConstructor
public class RecurringAvailabilityService {
    private final RecurringAvailabilityRepository repository;
    private final SpecificAvailabilityService specificAvailabilityService;

    public Flux<RecurringAvailability> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<RecurringAvailability> findById(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return Mono.fromCallable(() -> repository.findById(id))
                .flatMap(optional -> optional.map(Mono::just)
                        .orElseGet(Mono::empty));
    }

    public Mono<RecurringAvailability> save(RecurringAvailability availability) {
        if (availability == null) {
            return Mono.error(new IllegalArgumentException("Availability cannot be null"));
        }
        if (availability.getStartTime() == null || availability.getEndTime() ==  null) {
            return Mono.error(new IllegalArgumentException("Date or time cannot be null"));
        }

        if (availability.getStartTime().isAfter(availability.getEndTime())) {
            return Mono.error(new IllegalArgumentException("Start Time cannot be after end time"));
        }

        return Mono.fromCallable(() -> repository.save(availability));
    }

    public Mono<Void> deleteSeries(UUID id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }

        return findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the availability with the given ID")))
                .then(Mono.fromRunnable(() -> repository.deleteById(id)));
    }

    public Mono<Void> deleteSingleEntry(UUID id, LocalDate dateToRemove) {
        if (id == null || dateToRemove == null) {
            return Mono.error(new IllegalArgumentException("ID or date cannot be null"));
        }

        return findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cannot find the availability with the given ID")))
                .flatMap(item -> specificAvailabilityService.save(
                        dateToRemove, item.getStartTime(), item.getEndTime(), false
                ))
                .then();
    }
}
