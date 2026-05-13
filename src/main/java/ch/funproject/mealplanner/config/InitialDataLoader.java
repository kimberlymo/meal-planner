package ch.funproject.mealplanner.config;

import ch.funproject.mealplanner.domain.dto.RecipeDto;
import ch.funproject.mealplanner.service.RecipeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Component that loads initial data into the application on startup. It reads a JSON file containing recipe data and saves it to the database using the RecipeService.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataLoader implements CommandLineRunner {
    private final static String DATA_PATH = "/recipes.json";

    private final RecipeService recipeService;

    /**
     * Initializes starter data by reading a JSON file and saving the recipes to the database.
     * It uses Jackson's ObjectMapper to parse the JSON data into RecipeDto objects.
     * If there is an error during loading, it logs the error and throws a RuntimeException.
     */
    private void initializeData() {
        log.info("=".repeat(50));
        log.info("INITIALIZING STARTER DATA");
        log.info("=".repeat(50));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try (InputStream inputStream = getClass().getResourceAsStream(DATA_PATH)) {
            List<RecipeDto> recipes = mapper.readValue(inputStream, new TypeReference<>() {});
            recipes.forEach(recipeDto -> recipeService.save(recipeDto).subscribe());

        } catch (IOException e) {
            log.error("Could not load initial data! ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(String... args) {
        initializeData();
    }
}
