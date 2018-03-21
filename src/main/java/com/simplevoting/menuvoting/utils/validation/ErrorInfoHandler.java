package com.simplevoting.menuvoting.utils.validation;

import com.simplevoting.menuvoting.utils.MessageUtil;
import com.simplevoting.menuvoting.utils.exception.EditClosedPeriodException;
import com.simplevoting.menuvoting.utils.exception.ErrorInfo;
import com.simplevoting.menuvoting.utils.exception.ErrorType;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static com.simplevoting.menuvoting.utils.exception.ErrorType.DATA_NOT_FOUND;
import static com.simplevoting.menuvoting.utils.exception.ErrorType.EDIT_CLOSED_PERIOD_ERROR;
import static com.simplevoting.menuvoting.utils.exception.ErrorType.VALIDATION_ERROR;
import static org.slf4j.LoggerFactory.getLogger;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ErrorInfoHandler {
    private static final Logger log = getLogger(ErrorInfoHandler.class);

    @Autowired
    MessageUtil messageUtil;

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({NotFoundException.class, EditClosedPeriodException.class})
    public ErrorInfo notFoundErrorHandle(HttpServletRequest req, Exception e) {
        ErrorType type;
        if (e instanceof NotFoundException) {
            type = DATA_NOT_FOUND;
        } else {
            type = EDIT_CLOSED_PERIOD_ERROR;
        }
        return logAndGetErrorInfo(req, e, false, type, e.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo bindValidationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        String[] details = result.getFieldErrors().stream()
                .map(fe -> messageUtil.getMessage(fe))
                .toArray(String[]::new);

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, details);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String... details) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return new ErrorInfo(req.getRequestURL(), errorType,
                messageUtil.getMessage(errorType.getErrorCode()),
                details.length != 0 ? details : new String[]{rootCause.toString()});
    }
}
