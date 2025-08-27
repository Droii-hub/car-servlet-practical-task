package com.walking.carpractice;

public enum ErrorCode {
    NOT_FOUND(100, "Object not found", 404),
    WRONG_REQUEST(400, "Server can not handle this request", 400),
    CAR_NOT_FOUND(101, "Car not found", 404),
    DUPLICATE(200, "Object has duplicate", 500),
    // Коды для иных видов ошибки
    UNKNOWN(900, "Unknown error", 500);

    private final int internalCode;
    private final String message;
    private final int httpCode;

    ErrorCode(int internalCode, String message, int httpCode){
        this.internalCode=internalCode;
        this.message=message;
        this.httpCode=httpCode;
    }

    public int getInternalCode() {
        return internalCode;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
