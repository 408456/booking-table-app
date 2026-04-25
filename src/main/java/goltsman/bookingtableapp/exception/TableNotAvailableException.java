package goltsman.bookingtableapp.exception;

public class TableNotAvailableException extends RuntimeException {
    public TableNotAvailableException(String message) {
        super(message);
    }
}
