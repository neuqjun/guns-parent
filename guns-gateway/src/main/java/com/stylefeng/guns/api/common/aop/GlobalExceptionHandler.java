package com.stylefeng.guns.api.common.aop;

import com.stylefeng.guns.api.cinema.exception.CinemaQueryException;
import com.stylefeng.guns.api.cinema.vo.ErrorResp;
import com.stylefeng.guns.api.common.exception.CinemaExceptionEnum;
import com.stylefeng.guns.core.aop.BaseControllerExceptionHandler;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.api.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.exception.SystemException;
import com.stylefeng.guns.core.exception.SystemExceptionEnum;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
public class GlobalExceptionHandler extends BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截jwt相关异常
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorTip jwtException(JwtException e) {
        return new ErrorTip(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage());
    }
    /**
     * 拦截影院查询异常
     */
    @ExceptionHandler(CinemaQueryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResp cinemaQueryException(CinemaQueryException e) {
        return new ErrorResp(CinemaExceptionEnum.QUERY_ERROR.getStatus(), CinemaExceptionEnum.QUERY_ERROR.getMsg());
    }

    /**
     * 拦截系统异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResp notFount(RuntimeException e) {
        log.error("系统异常:", e);
        return new ErrorResp(SystemExceptionEnum.SYSTEM_ERROR.getStatus(), SystemExceptionEnum.SYSTEM_ERROR.getMsg());
    }
}
