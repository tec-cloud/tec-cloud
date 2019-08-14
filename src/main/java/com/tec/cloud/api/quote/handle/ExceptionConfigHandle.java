package com.tec.cloud.api.quote.handle;

import com.tec.platform.common.core.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 异常处理类
 * <p>
 * 可以指定http返回的状态码
 *
 * @author tec
 */
@RestControllerAdvice
@ResponseStatus(HttpStatus.OK)
public class ExceptionConfigHandle {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    public CommonResult handleMultipartException(IllegalArgumentException ex, HttpServletRequest request) {
        logger.error("请求参数报错 uri: {}, message: {} ", request.getRequestURI(), ex.getMessage());
        return CommonResult.fail(CommonResult.Argument_ERROR, "请求参数错误：" + ex.getMessage());
    }

}
