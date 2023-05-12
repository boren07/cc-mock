package com.borened.mock.annotation;

import com.borened.mock.config.MockConfig;

import java.lang.annotation.*;

/**
 * mock标记
 *
 * @author cch
 * @since 2023-05-10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Documented
public @interface Mock {

}
