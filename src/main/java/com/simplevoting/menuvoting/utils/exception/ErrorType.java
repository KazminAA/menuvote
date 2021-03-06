package com.simplevoting.menuvoting.utils.exception;

public enum ErrorType {
    APP_ERROR("error.appError"),
    DATA_NOT_FOUND("error.dataNotFound"),
    DATA_ERROR("error.dataError"),
    EDIT_CLOSED_PERIOD_ERROR("error.editClosedPeriod"),
    VALIDATION_ERROR("error.validationError");

    private final String errorCode;

    ErrorType(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}