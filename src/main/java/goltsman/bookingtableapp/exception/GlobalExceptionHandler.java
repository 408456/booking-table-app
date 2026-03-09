package goltsman.bookingtableapp.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        String errorMessage = String.join(", ", errorMessages);
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessage, ex);
    }

    @ExceptionHandler({
            NumberFormatException.class,
            IllegalArgumentException.class,
            MissingPathVariableException.class,
            ConstraintViolationException.class,
            IllegalStateException.class
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Некорректный формат запроса. Проверьте JSON и кавычки.",
                ex);
    }

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<HttpErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                "Отсутствует или некорректный токен аунтификации",
                ex
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(
                HttpStatus.FORBIDDEN,
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                StringUtils.hasText(ex.getMessage()) ?
                        ex.getMessage() : "Недостаточно прав для выполнения данного действия",
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> handleEntityInternalServerException(Exception ex) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Неизвестная ошибка сервера. Попробуйте снова",
                ex);
    }

}
