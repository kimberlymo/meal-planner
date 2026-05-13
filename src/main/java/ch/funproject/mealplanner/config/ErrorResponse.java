package ch.funproject.mealplanner.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A standardized Data Transfer Object (DTO) for conveying error details to the client.
 * <p>
 * This class is used by the {@link GlobalExceptionHandler} to provide a consistent
 * JSON structure whenever an exception occurs during API execution.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode; // HTTP status code
    private String message;

    /**
     * Minimalist constructor for creating a response with just a message.
     * <p>
     * Note: When using this constructor, the {@code statusCode} will default to 0
     * unless manually set later via the setter.
     *
     * @param message the error message to display.
     */
    public ErrorResponse(String message) {
        super();
        this.message = message;
    }
}