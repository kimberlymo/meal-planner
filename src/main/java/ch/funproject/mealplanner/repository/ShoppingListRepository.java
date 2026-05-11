package ch.funproject.mealplanner.repository;

import ch.funproject.mealplanner.domain.Meal;
import ch.funproject.mealplanner.domain.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for {@link ShoppingList} entities.
 * <p>
 * This interface leverages Spring Data JPA to provide standard asynchronous-capable
 * CRUD operations against the persistence layer.
 */
@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {
    ShoppingList findFirstByOrderByCreationDateDesc();
}
