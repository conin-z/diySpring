package com.spring.ioc.marktypes;

import java.lang.annotation.*;

/**
 * @user KyZhang
 * @date
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
@Documented
public @interface Component {
    String value() default "";
}
