package icu.junyao.fileservice.exception.assertion;

import icu.junyao.fileservice.enums.ResponseEnum;
import icu.junyao.fileservice.exception.BaseException;
import icu.junyao.fileservice.exception.BusinessException;

import java.text.MessageFormat;

/**
 * @author johnson
 * @date 2021-10-02
 */
public interface BusinessExceptionAssert extends ResponseEnum, icu.junyao.fileservice.exception.assertion.Assert {

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }

}
