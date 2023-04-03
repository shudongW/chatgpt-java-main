package com.tech.chatgpt.exception;

/**
 * 描述： 异常
 *
 * @author wsd
 */
public class BaseException extends RuntimeException {

    private String msg;
    private int code;

    public BaseException(IError error) {
        super(error.msg());
        this.code = error.code();
        this.msg = error.msg();
    }

    public BaseException(String msg) {
        super(msg);
        this.code = CommonError.SYS_ERROR.code();
        this.msg = msg;
    }

    public BaseException() {
        super(CommonError.SYS_ERROR.msg());
        this.code = CommonError.SYS_ERROR.code();
        this.msg = CommonError.SYS_ERROR.msg();
    }
}
