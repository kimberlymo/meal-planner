package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.RecurringAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for {@link RecurringAvailability} entities.
 * <p>
 * This interface leverages Spring Data JPA to provide standard asynchronous-capable
 * CRUD operations against the persistence layer.
 */
@Repository
public interface RecurringAvailabilityRepository extends JpaRepository<RecurringAvailability, UUID> {
}
