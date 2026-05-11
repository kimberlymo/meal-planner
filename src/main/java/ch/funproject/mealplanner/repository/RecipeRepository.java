package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for {@link Recipe} entities.
 * <p>
 * This interface leverages Spring Data JPA to provide standard asynchronous-capable
 * CRUD operations against the persistence layer.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findByDurationIsLessThanEqualOrderByDurationDesc(int maxDuration);
}
