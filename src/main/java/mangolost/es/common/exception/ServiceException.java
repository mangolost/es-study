package mangolost.es.common.exception;

/**
 * Created by mangolost on 2017-04-22
 */
public class ServiceException extends RuntimeException {

    private int code;

    public ServiceException() {
        super();
    }

    /**
     * 通过code查询异常信息
     */
    public ServiceException(int code) {
        super();
        this.code = code;
    }

    /**
     * 使用 cause的message信息
     *
     * @param code  错误码
     * @param cause 错误ex
     */
    public ServiceException(int code, Throwable cause) {
        this(code, cause.getMessage(), cause);
    }

    /**
     * 自定义的异常信息
     */
    public ServiceException(int code, String errorMsg) {
        super(errorMsg);
        this.code = code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
