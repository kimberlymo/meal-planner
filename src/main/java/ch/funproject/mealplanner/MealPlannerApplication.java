package ch.funproject.mealplanner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Meal Planner Application.
 * <p>
 * This class initializes the Spring Boot framework, performs component scanning,
 * and configures the auto-configuration settings. It also defines the global
 * OpenAPI documentation metadata for the project's REST interface.
 */
@OpenAPIDefinition(
		info = @Info(
				title = "Meal Planner API",
				version = "1.0",
				description = "API documentation for managing meals during the week"
		)
)
@SpringBootApplication
public class MealPlannerApplication {

	/**
	 * Standard main method used to launch the application.
	 *
	 * @param args command-line arguments passed to the application at runtime.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MealPlannerApplication.class, args);
	}
}
