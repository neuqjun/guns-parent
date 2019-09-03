package com.stylefeng.guns.api.cinema.exception;

/**
 * 影院查询异常
 */
public class CinemaQueryException extends RuntimeException {

    private static final long serialVersionUID = -1965611730799700810L;

    public CinemaQueryException(String message) {
        super(message);
    }

    public CinemaQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
