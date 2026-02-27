package goltsman.bookingtableapp.exception;

public record HttpErrorResponse(
        int code,
        String type,
        String message
) {
}
