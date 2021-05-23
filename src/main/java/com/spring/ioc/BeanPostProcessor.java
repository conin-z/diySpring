package com.spring.ioc;

/**
 * @user KyZhang
 * @date
 */
public interface BeanPostProcessor {

    Object processBeforeInitializing();

    Object processAfterInitializing();


}
