package com.simplevoting.menuvoting.utils.validation;


import com.simplevoting.menuvoting.HasId;
import com.simplevoting.menuvoting.model.AbstractBaseEntity;
import com.simplevoting.menuvoting.utils.MessageUtil;
import com.simplevoting.menuvoting.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {

    private static MessageUtil messageUtil;

    private ValidationUtil() {
    }

    @Autowired
    public ValidationUtil(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException(messageUtil.getMessage("exception.common.notFound", msg));
        }
    }

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId entity, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}