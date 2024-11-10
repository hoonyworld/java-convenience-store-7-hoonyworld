package store.exception;

public enum ArgumentErrorMessage implements ExceptionType {
    INVALID_MONEY("금액은 0 이상의 값이어야 합니다."),
    INVALID_NAME("이름은 null이거나 빈 값일 수 없습니다."),
    INVALID_QUANTITY("수량은 0보다 크거나 같아야 합니다.");

    private final String message;

    ArgumentErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
