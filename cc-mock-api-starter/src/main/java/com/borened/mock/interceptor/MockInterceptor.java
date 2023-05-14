package com.borened.mock.interceptor;


import com.alibaba.fastjson2.JSON;
import com.borened.mock.CcMock;
import com.borened.mock.MockApiResultWrapper;
import com.borened.mock.annotation.MockResponse;
import com.borened.mock.config.MockApiProperties;
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
    private final MockApiProperties mockApiProperties;

    public MockInterceptor(MockApiResultWrapper<?> mockApiResultWrapper, MockApiProperties mockApiProperties) {
        Assert.notNull(mockApiResultWrapper,"api result wrapper not be null");
        Assert.notNull(mockApiProperties,"mockApiProperties not be null");
        this.mockApiResultWrapper = mockApiResultWrapper;
        this.mockApiProperties = mockApiProperties;
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
            ParameterizedType returnType =  (ParameterizedType) genericType;
            Type[] actualTypeArguments = returnType.getActualTypeArguments();
            mockResponse  =  mockApiResultWrapper.wrapper(CcMock.mock(mockApiProperties.getGeneralConfig(),actualTypeArguments[0]));
        } else {
            MockResponse methodAnnotation = handlerMethod.getMethodAnnotation(MockResponse.class);
            MockResponse classAnnotation = handlerMethod.getBean().getClass().getAnnotation(MockResponse.class);
            //注解优先
            if (methodAnnotation!=null) {
                mockResponse = mockApiResultWrapper.wrapper(CcMock.mock(mockApiProperties.getGeneralConfig(),methodAnnotation.dataType()));
            }else if (classAnnotation!=null) {
                mockResponse = mockApiResultWrapper.wrapper(CcMock.mock(mockApiProperties.getGeneralConfig(),classAnnotation.dataType()));
            } else {
                mockResponse = mockApiResultWrapper.wrapper(null);
            }
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
