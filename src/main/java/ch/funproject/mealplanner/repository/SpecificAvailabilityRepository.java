package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.SpecificAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpecificAvailabilityRepository extends JpaRepository<SpecificAvailability, UUID> {
    List<SpecificAvailability> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
