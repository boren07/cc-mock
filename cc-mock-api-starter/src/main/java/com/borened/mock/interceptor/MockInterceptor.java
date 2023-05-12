package com.borened.mock.interceptor;


import com.alibaba.fastjson2.JSON;
import com.borened.mock.CcMock;
import com.borened.mock.MockApiResultWrapper;
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
//@Slf4j
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
        Object mockResponse;
        if (genericType instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
            mockResponse  =  mockApiResultWrapper.wrapper(CcMock.mock(actualTypeArguments[0]));
        } else {
            mockResponse = mockApiResultWrapper.wrapper(null);
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
