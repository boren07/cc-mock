package com.borened.mock.interceptor;


import com.alibaba.fastjson2.JSON;
import com.borened.mock.CcMock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * mock响应增强处理
 * @author cch
 */
@Slf4j
public class MockInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        MethodParameter methodParameter = handlerMethod.getReturnType();
        Class<?> type = methodParameter.getGenericParameterType().getClass();
        Object mockResponse = CcMock.mock(type);
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
