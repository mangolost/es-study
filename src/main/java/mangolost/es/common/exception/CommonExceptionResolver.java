package mangolost.es.common.exception;

import mangolost.es.common.CommonResult;
import mangolost.es.common.utils.ThrowableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * Created by mangolost on 2017-04-13
 */
@ControllerAdvice
//public class CommonExceptionResolver implements HandlerExceptionResolver {
public class CommonExceptionResolver {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommonExceptionResolver.class);

    @Value("${system.runmode}")
    private String runMode;

    /**
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public CommonResult resolveException(HttpServletResponse response, Exception ex) {

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        int code = 200;
        String message = "OK";

        try {

            String exMsg = "";
            if ("dev".equals(runMode) || "uat1".equals(runMode) || "stg".equals(runMode)) {
                exMsg = ": " + ex.getMessage();
            }

            //根据异常类型返回对应的code与message
            if (ex instanceof MissingServletRequestParameterException
                    || ex instanceof MethodArgumentTypeMismatchException
                    || ex instanceof NumberFormatException
                    || ex instanceof BindException
                    || ex instanceof ConstraintViolationException) {
                code = 430;
                message = "参数错误 " + exMsg;
            } else if (ex instanceof HttpRequestMethodNotSupportedException) {
                code = 405; // 405 Method Not Allowed
                message = "服务器不支持该方法 " + exMsg;
            } else if (ex instanceof HttpMediaTypeNotSupportedException) {
                // 415 Content type not supported
                code = 415;
                message = "服务器不支持该媒体类型 " + exMsg;
            } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                // 406 Could not find acceptable representation
                code = 406;
                message = "无法使用请求的内容特性来响应请求 " + exMsg;
            } else if (ex instanceof ServiceException) {
                // 自定义异常
                ServiceException se = (ServiceException) ex;
                if (se.getCode() != 0) {
                    code = se.getCode();
                }
                if (code == 200) {
                    code = 500;
                }
                if (se.getMessage() != null) {
                    message = se.getMessage();
                }
            } else {

                code = 500;
                if ("dev".equals(runMode) || "uat1".equals(runMode) || "stg".equals(runMode)) {
                    exMsg += "\r\n" + ThrowableUtils.printStackTraceToString(ex);
                }
                message = "服务器内部异常 " + exMsg;
                LOGGER.error("服务器内部异常: ", ex);
            }
        } catch (Exception e) {
            LOGGER.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", e);
        }

        return buildCommonResult(code, message);

    }

    /**
     * @param code
     * @param message
     * @return
     */
    // 返回结果处理, JSON字符串化的CommonResult对象
    private CommonResult buildCommonResult(int code, String message) {
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(code);
        commonResult.setMessage(message);
        return commonResult;
    }
}