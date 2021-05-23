package com.spring.ioc.marktypes;


import com.spring.ioc.AliasFor;

import java.lang.annotation.*;

/**
 * @user KyZhang
 * @date
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Service {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
