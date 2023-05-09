package com.borened.mock.interceptor;


import com.alibaba.fastjson2.JSON;
import com.borened.mock.MockApiResultWrapper;
import com.borened.mock.CcMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * mock响应增强处理
 * @author cch
 */
@Slf4j
public class MockInterceptor implements HandlerInterceptor {

    private final MockApiResultWrapper<?> mockApiResultWrapper;

    public MockInterceptor(MockApiResultWrapper<?> mockApiResultWrapper) {
        Assert.notNull(mockApiResultWrapper,"api result wrapper not be null");
        this.mockApiResultWrapper = mockApiResultWrapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        MethodParameter methodParameter = handlerMethod.getReturnType();
        Type genericType = methodParameter.getGenericParameterType();
        Object mockResponse = null;
        //泛型响应处理
        if (genericType instanceof ParameterizedType) {
            Type[] args = ((ParameterizedType) genericType).getActualTypeArguments();
            if (args.length > 0 && args[0] instanceof Class) {
                Class<?> argType = (Class<?>) args[0];
                Object mock = CcMock.mock(argType);
                mockResponse = mockApiResultWrapper.wrapper(mock);
            }
        }else {
            Class<?> clazz = (Class<?>) genericType;
            mockResponse = CcMock.mock(clazz);
        }
        writeResp(response, JSON.toJSONString(mockResponse), "application/json;charset=utf-8");
        return false;
    }

    private void writeResp(HttpServletResponse response, String text, String contentType) throws IOException {
        response.setContentType(contentType);
        try (Writer writer = response.getWriter()) {
            writer.write(text);
            writer.flush();
        }
    }
}
