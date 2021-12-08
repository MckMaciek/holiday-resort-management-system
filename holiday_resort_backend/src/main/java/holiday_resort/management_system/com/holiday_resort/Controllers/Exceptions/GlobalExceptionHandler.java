package holiday_resort.management_system.com.holiday_resort.Controllers.Exceptions;

import holiday_resort.management_system.com.holiday_resort.Exceptions.ApiErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<ApiErrorException> illegalArgumentProvided(
            IllegalArgumentException ex, WebRequest request) {

        ApiErrorException message = ApiErrorException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .errorTime(LocalDateTime.now())
                .errorMessage(ex.getMessage())
                .build();

        request.getDescription(false);

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
