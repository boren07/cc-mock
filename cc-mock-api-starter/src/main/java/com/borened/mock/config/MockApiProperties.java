package com.borened.mock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mock api配置
 *
 * @author cch
 * @since 2023-04-26
 */
@Data
@ConfigurationProperties(prefix = "ccmock")
public class MockApiProperties {

    /**
     * mock通用规则配置
     */
    private MockConfig generalConfig = new MockConfig();
    /*
     是否开启mock模式，默认开启
     */
    private boolean enabled =true;

}
