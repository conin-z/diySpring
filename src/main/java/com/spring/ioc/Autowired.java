package com.spring.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @user KyZhang
 * @date
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    boolean required() default true;
}
