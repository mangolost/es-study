package mangolost.es.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by mangolost on 2017-04-24
 */
public class CommonResult {

    private int code = 200;
    private String message = "OK";
    private Object data = null;

    public CommonResult() {

    }

    public CommonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CommonResult setCodeAndMessage(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public CommonResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}