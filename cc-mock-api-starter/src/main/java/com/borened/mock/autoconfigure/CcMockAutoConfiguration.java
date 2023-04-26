package com.borened.mock.autoconfigure;

import com.borened.mock.CcMock;
import com.borened.mock.config.MockApiProperties;
import com.borened.mock.config.MockMvcConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mock自动配置类
 *
 * @author cch
 * @since 2023-04-26
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(CcMock.class)
@ConditionalOnProperty(name = "ccmock.enabled", havingValue = "true")
@EnableConfigurationProperties(MockApiProperties.class)
public class CcMockAutoConfiguration {

    @Bean
    public MockMvcConfig mockMvcConfig(){
        return new MockMvcConfig();
    }
}
