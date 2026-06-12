# MEAL PLANNER

This project is a meal planner application built with Spring Boot, Spring WebFlux, Spring Data JPA, and H2 Database. It provides a RESTful API for managing meal plans, recipes, and ingredients.

## Technologies Used

*   **Spring Boot**: Framework for building stand-alone, production-grade Spring applications.
*   **Spring WebFlux**: Reactive web framework for building asynchronous and non-blocking applications.
*   **Spring Data JPA**: Simplifies data access with JPA repositories.
*   **H2 Database**: In-memory relational database for development and testing.
*   **Gradle**: Build automation tool.
*   **Swagger UI**: For interactive API documentation.
*   **Lombok**: Reduces boilerplate code.
*   **MapStruct**: Code generator for type-safe bean mappings.

## Prerequisites

Before you begin, ensure you have met the following requirements:

*   Java Development Kit (JDK) 21 or later
*   Gradle (usually bundled with the project as a wrapper)

## Project Structure

The core structure of the project is as follows:

- `src/main/java/ch/funproject/mealplanner/`: Contains the main application code.
    - `MealPlannerApplication.java`: The main entry point of the Spring Boot application.
    - `config/`: Configuration classes for the application, including error handling, H2 console, and initial data loading.
    - `controller/`: REST controllers handling API requests.
    - `domain/`: Domain models and entities.
    - `repository/`: Spring Data JPA repositories for data access.
    - `service/`: Business logic and service implementations.
- `src/main/resources/`: Contains application resources.
    - `application.properties`: Application-specific properties and configurations.
    - `availabilities.json`: Data for preloading availabilities into the H2 database.
    - `recipes.json`: Data for preloading recipes into the H2 database.
    - `static/`: Static web resources.
    - `templates/`: HTML templates (if any).

## How to Run

To run the application, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd meal-planner
    ```
2.  **Build the project (optional):**
    ```bash
    ./gradlew build
    ```
3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```
    The application will start on `http://localhost:8080`.

## Swagger UI

The API documentation is available through Swagger UI. Once the application is running, you can access it at:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

This interface allows you to explore the available API endpoints, their request/response formats, and even test them directly.

## API Information

The API provides endpoints for managing various aspects of the meal planner. Here are some of the most important API calls:

- **Recipes:**
    - `GET /api/recipes`: Get all recipes.
    - `GET /api/recipes/{id}`: Get a recipe by ID.
    - `POST /api/recipes`: Create a new recipe.
    - `PUT /api/recipes/{id}`: Update an existing recipe.
    - `DELETE /api/recipes/{id}`: Delete a recipe.

- **Ingredients:**
    - `GET /api/ingredients`: Get all ingredients.
    - `GET /api/ingredients/{id}`: Get an ingredient by ID.
    - `POST /api/ingredients`: Create a new ingredient.
    - `PUT /api/ingredients/{id}`: Update an existing ingredient.
    - `DELETE /api/ingredients/{id}`: Delete an ingredient.

- **Meal Plans:**
    - `GET /api/mealplans`: Get all meal plans.
    - `GET /api/mealplans/{id}`: Get a meal plan by ID.
    - `POST /api/mealplans`: Create a new meal plan.
    - `PUT /api/mealplans/{id}`: Update an existing meal plan.
    - `DELETE /api/mealplans/{id}`: Delete a meal plan.

Please refer to the Swagger UI for a complete and up-to-date list of all available endpoints and their detailed specifications.

## H2 Database Preloading

The application uses an in-memory H2 database for development and testing purposes. Initial data is preloaded into the database on application startup.

The preloading is handled by the `InitialDataLoader` class (located in `src/main/java/ch/funproject/mealplanner/config/InitialDataLoader.java`). This class reads data from the following JSON files located in `src/main/resources/`:

- `availabilities.json`: Contains initial availability data.
- `recipes.json`: Contains initial recipe data.

You can modify these JSON files to change the initial data loaded into the H2 database.

### H2 Console

For local development, you can access the H2 console to inspect the database content. It is available at:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

You will need to provide the JDBC URL, username, and password configured in `application.properties`. By default, these are:

- **JDBC URL:** `jdbc:h2:mem:mealplannerdb`
- **Username:** `sa`
- **Password:** (empty)

