package com.borened.mock.annotation;

import java.lang.annotation.*;

/**
 * mock标记
 *
 * @author cch
 * @since 2023-05-10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
@Documented
public @interface MockIgnore {

}
