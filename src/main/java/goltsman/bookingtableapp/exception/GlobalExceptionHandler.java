package goltsman.bookingtableapp.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<HttpErrorResponse> buildErrorResponse(HttpStatus status,
                                                                 String type,
                                                                 String message,
                                                                 Exception ex) {
        log.error(type, ex);
        HttpErrorResponse errorResponse = new HttpErrorResponse(status.value(), type, message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler({
            NumberFormatException.class,
            IllegalArgumentException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<HttpErrorResponse> handleBadRequest(Exception ex) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                StringUtils.hasText(ex.getMessage()) ?
                        ex.getMessage() : "Неправильные аргументы запроса",
                ex
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<HttpErrorResponse> handleConflict(ResourceAlreadyExistsException ex) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                HttpStatus.CONFLICT.getReasonPhrase(),
                StringUtils.hasText(ex.getMessage()) ?
                        ex.getMessage() : "Ресурс уже существует",
                ex
        );
    }

    @ExceptionHandler({EntityNotFoundException.class,})
    public ResponseEntity<HttpErrorResponse> handleNotFound(EntityNotFoundException ex) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                StringUtils.hasText(ex.getMessage()) ?
                        ex.getMessage() : "Запрашиваемый ресурс не найден",
                ex
        );
    }

    @ExceptionHandler({AuthenticationException.class,})
    public ResponseEntity<HttpErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                StringUtils.hasText(ex.getMessage()) ?
                        ex.getMessage() : "У вас нет прав для выполнения данного действия",
                ex
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                ex
        );
    }

}
