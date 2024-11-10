package store.exception;

public enum ArgumentErrorMessage implements ExceptionType {
    ;

    private final String message;

    ArgumentErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
