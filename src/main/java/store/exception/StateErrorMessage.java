package store.exception;

public enum StateErrorMessage implements ExceptionType {
    FILE_OPERATION_ERROR("파일 처리 중 오류가 발생했습니다. 경로와 파일 접근 권한을 확인하세요.");

    private final String message;

    StateErrorMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
