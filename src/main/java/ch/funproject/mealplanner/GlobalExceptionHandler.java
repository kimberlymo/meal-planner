package ch.funproject.mealplanner;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

/**
 * Global interceptor for handling exceptions across the entire application.
 * <p>
 * This class captures specific exceptions thrown by controllers or services and
 * transforms them into standardized {@link ErrorResponse} objects with appropriate
 * HTTP status codes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation and bad input errors.
     * <p>
     * Typically triggered when service layer assertions fail or when invalid
     * parameters are provided to a request.
     *
     * @param exception the caught {@link IllegalArgumentException}.
     * @return an {@link ErrorResponse} containing a 400 Bad Request status and the error message.
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    /**
     * Handles cases where a requested resource could not be found in the database.
     * <p>
     * Maps the standard Java {@link NoSuchElementException} to a REST-friendly 404 signal.
     *
     * @param exception the caught {@link NoSuchElementException}.
     * @return an {@link ErrorResponse} containing a 404 Not Found status and the error message.
     */
    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleNoSuchElementException(NoSuchElementException exception) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());

    }
}
