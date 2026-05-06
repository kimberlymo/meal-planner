package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.PlannedMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlannedMealRepository extends JpaRepository<PlannedMeal, UUID> {
}
