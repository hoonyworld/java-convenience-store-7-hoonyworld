package store.exception;

public class StoreStateException extends IllegalStateException {
    private static final String ERROR_PREFIX = "[ERROR] ";

    private StoreStateException(StateErrorMessage message) {
        super(ERROR_PREFIX + message.getMessage());
    }

    public static StoreStateException from(StateErrorMessage message) {
        return new StoreStateException(message);
    }
}
