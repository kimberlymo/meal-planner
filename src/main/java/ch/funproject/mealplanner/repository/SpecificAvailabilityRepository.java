package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.SpecificAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link SpecificAvailability} entities.
 * <p>
 * This interface leverages Spring Data JPA to provide standard asynchronous-capable
 * CRUD operations against the persistence layer.
 */
@Repository
public interface SpecificAvailabilityRepository extends JpaRepository<SpecificAvailability, UUID> {
    List<SpecificAvailability> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
