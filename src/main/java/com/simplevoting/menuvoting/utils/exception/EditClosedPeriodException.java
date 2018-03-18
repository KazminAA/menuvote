package com.simplevoting.menuvoting.utils.exception;

import com.simplevoting.menuvoting.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class EditClosedPeriodException extends RuntimeException {
    private static final String msgCode = "error.editClosedPeriod";

    public EditClosedPeriodException() {
        super(msgCode);
    }

    public static String getMsgCode() {
        return msgCode;
    }
}
