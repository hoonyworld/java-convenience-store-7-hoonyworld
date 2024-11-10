package store.exception;

public class StoreArgumentException extends IllegalArgumentException {
    private static final String ERROR_PREFIX = "[ERROR] ";

    private StoreArgumentException(ArgumentErrorMessage message) {
        super(ERROR_PREFIX + message.getMessage());
    }

    public static StoreArgumentException from(ArgumentErrorMessage message) {
        return new StoreArgumentException(message);
    }
}
