package com.stylefeng.guns.core.exception;

/**
 * 系统异常
 */
public class SystemException extends RuntimeException{

    private static final long serialVersionUID = -2853791859295192897L;

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
