package com.borened.mock;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 自定义响应结果,覆盖默认的MOCK响应包装
 * @author cch
 * @since 2023-04-28
 */
@Component
@Data
public class MyResult<T> implements MockApiResultWrapper<MyResult<T>> {
    private String status;
    private String message;
    private T data;

    public MyResult() {
    }

    public MyResult(T data) {
        this.status="0000000";
        this.message="yes";
        this.data = data;
    }

    @Override
    public MyResult<T> wrapper(Object data) {
        return new MyResult<T>((T) data);
    }
}
