package ch.funproject.mealplanner.service.plan;

import ch.funproject.mealplanner.domain.RecurringAvailability;
import ch.funproject.mealplanner.domain.SpecificAvailability;
import ch.funproject.mealplanner.service.RecurringAvailabilityService;
import ch.funproject.mealplanner.service.SpecificAvailabilityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * AvailabilityService is responsible for managing and retrieving availability information for meal planning.
 * It combines specific availabilities with recurring availabilities to provide a comprehensive view of available time slots.
 * The service uses reactive programming with Project Reactor to handle asynchronous data retrieval and processing.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AvailabilityService {
    private final SpecificAvailabilityService specificAvailabilityService;
    private final RecurringAvailabilityService recurringAvailabilityService;

    /**
     * Retrieves all specific availabilities within the given date range.
     *
     * @param start the start of the date range
     * @param end   the end of the date range
     * @return a Flux containing all SpecificAvailability objects that fall within the specified date range
     */
    public Flux<SpecificAvailability> getAllAvailabilitiesInRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            return Flux.error(new IllegalArgumentException("Start date cannot be after end"));
        }

        var daysBetween = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
        var recurring = recurringAvailabilityService.findAll()
                .collectList()
                .flatMapMany(list -> buildNewAvailaibilities((int) daysBetween, list, start))
                .doOnComplete(() -> log.info("Generated specific availability slots from {} to {}", start, end));

        return Flux.merge(specificAvailabilityService.findByDateRange(start.toLocalDate(), end.toLocalDate()), recurring).distinct();
    }

    private Flux<SpecificAvailability> buildNewAvailaibilities(int daysBetween, List<RecurringAvailability> recurringList, LocalDateTime start) {
        return Flux.range(0, daysBetween + 1)
                .map(offset -> start.toLocalDate().plusDays(offset))
                .flatMap(currentDate ->
                        // 3. Filter rules that match the current day of the week
                        Flux.fromIterable(recurringList)
                                .filter(recurring -> recurring.getWeekDay().equals(currentDate.getDayOfWeek()))
                                .map(recurring -> mapSpecific(recurring, currentDate))
                );
    }

    private SpecificAvailability mapSpecific(RecurringAvailability recurring, LocalDate date) {
        return SpecificAvailability.builder()
                .date(date)
                .startTime(recurring.getStartTime())
                .endTime(recurring.getEndTime())
                .isAvailable(true)
                .build();
    }
}
