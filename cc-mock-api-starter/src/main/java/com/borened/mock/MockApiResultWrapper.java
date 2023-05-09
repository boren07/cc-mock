package com.borened.mock;

/**
 * mock api响应结果包装
 *
 * @author cch
 * @since 2023-04-28
 */
public interface MockApiResultWrapper<T> {
    /**
     * 包装接口响应体的逻辑实现
     * @param res 原始数据
     * @return 包装后的响应数据
     */
    T wrapper(Object res);
}
