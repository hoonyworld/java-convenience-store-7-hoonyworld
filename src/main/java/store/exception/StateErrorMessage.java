package store.exception;

public enum StateErrorMessage implements ExceptionType {
    ;

    private final String message;

    StateErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
