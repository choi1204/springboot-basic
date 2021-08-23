package com.prgms.kdtspringorder.ui.message;

public enum ErrorMessage {
    INVALID_COMMAND("유효하지 않은 명령어입니다. 명령어 종류를 보고 싶다면 'help'를 입력하세요."),
    INVALID_VOUCHER_TYPE("존재하지 않는 바우처 종류입니다."),
    INVALID_DISCOUNT_PERCENT("100% 초과 할인은 불가능합니다."),
    INVALID_DISCOUNT_AMOUNT("할인 금액이 원가를 초과합니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}