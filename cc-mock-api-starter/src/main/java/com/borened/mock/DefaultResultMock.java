package com.borened.mock;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 默认的mock 响应信息主体
 *
 * @author cch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DefaultResultMock<T> implements Serializable, MockApiResultWrapper<DefaultResultMock<T>> {
    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;
    private T data;
    public static <T> DefaultResultMock<T> ok(){
        return restResult(null, 0, "OK");
    }
    public static <T> DefaultResultMock<T> ok(T data){
        return restResult(data, 0, "OK");
    }
    public static <T> DefaultResultMock<T> fail(T data){
        return restResult(data, -1, "ERROR");
    }
    private static <T> DefaultResultMock<T> restResult(T data, int code, String msg) {
        DefaultResultMock<T> apiResultWrapper = new DefaultResultMock<>();
        apiResultWrapper.setCode(code);
        apiResultWrapper.setMsg(msg);
        apiResultWrapper.setData(data);
        return apiResultWrapper;
    }

    @Override
    public DefaultResultMock<T> wrapper(Object res) {
        return (DefaultResultMock<T>) ok(res);
    }
}
