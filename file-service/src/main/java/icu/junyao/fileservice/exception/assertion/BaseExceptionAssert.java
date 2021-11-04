package icu.junyao.fileservice.exception.assertion;

import icu.junyao.fileservice.enums.ResponseEnum;
import icu.junyao.fileservice.exception.BaseException;

import java.text.MessageFormat;


/**
 * @author johnson
 * @date 2021-10-02
 */
public interface BaseExceptionAssert extends ResponseEnum, icu.junyao.fileservice.exception.assertion.Assert {

    /**
     * 创建异常
     *
     * @param args
     * @return
     */
    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BaseException(this, args, msg);
    }

    /**
     * 创建异常
     *
     * @param cause
     * @param args
     * @return
     */
    @Override
    default BaseException newException(Throwable cause, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BaseException(this, args, msg, cause);
    }

}
