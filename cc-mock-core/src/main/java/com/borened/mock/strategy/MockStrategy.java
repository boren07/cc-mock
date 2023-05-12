package com.borened.mock.strategy;


import com.borened.mock.config.MockConfig;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 动态表单数据处理策略
 *
 * @author cch
 * @since 2023-02-03
 */
public interface MockStrategy{
    /**
     * 策略支持的类型
     * @return 支持的类型class
     */
    List<Class<?>> supportTypes();

    /**
     * mock数据实现
     *
     * @param mockConfig mock配置
     * @param tClass     转换类型
     * @return T 模拟出来的对象
     */
    <T> T mock(MockConfig mockConfig,Class<T> tClass);

    /**
     * 根据类型mock
     *
     * @param mockConfig
     * @param type       类型
     * @return mock实例
     */
    default Object mock(MockConfig mockConfig, Type type){
        return null;
    }
}
