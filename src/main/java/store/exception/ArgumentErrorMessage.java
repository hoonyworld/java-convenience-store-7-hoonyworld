package store.exception;

public enum ArgumentErrorMessage implements ExceptionType {
    INVALID_MONEY("금액은 0 이상의 값이어야 합니다."),
    INVALID_NAME("이름은 null이거나 빈 값일 수 없습니다."),
    INVALID_QUANTITY("수량은 0보다 크거나 같아야 합니다."),
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    INVALID_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),
    PRODUCT_NOT_FOUND("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INSUFFICIENT_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_PROMOTION_FORMAT("프로모션 정보의 형식이 올바르지 않습니다. 입력값을 확인해주세요."),
    INVALID_DATE_FORMAT("날짜 형식이 잘못되었습니다. 올바른 형식(YYYY-MM-DD)인지 확인해주세요."),
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
