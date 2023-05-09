package com.borened.mock.config;

import com.borened.mock.interceptor.MockInterceptor;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cch
 * @since 2023-04-26
 */
public class MockMvcConfig implements WebMvcConfigurer {

    private final MockInterceptor mockInterceptor;

    public MockMvcConfig(MockInterceptor mockInterceptor) {
        Assert.notNull(mockInterceptor,"mockInterceptor not be null");
        this.mockInterceptor = mockInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mockInterceptor);
    }

}
