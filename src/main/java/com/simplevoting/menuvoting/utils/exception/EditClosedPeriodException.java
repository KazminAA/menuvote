package com.simplevoting.menuvoting.utils.exception;

public class EditClosedPeriodException extends RuntimeException {
    public EditClosedPeriodException() {
        super("Trying to edit a closed period.");
    }
}
