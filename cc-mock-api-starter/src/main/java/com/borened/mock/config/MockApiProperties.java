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

    private MockConfig config = new MockConfig();

    private boolean enabled =true;

}
