package com.spring.ioc;

import java.lang.annotation.*;

/**
 * @user KyZhang
 * @date
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AliasFor {
    @org.springframework.core.annotation.AliasFor("attribute")
    String value() default "";

    @org.springframework.core.annotation.AliasFor("value")
    String attribute() default "";

    Class<? extends Annotation> annotation() default Annotation.class;
}

