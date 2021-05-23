package com.rpc;

import com.spring.ioc.BeanPostProcessor;

/**
 * @user KyZhang
 * @date
 */
public class AopBPP implements BeanPostProcessor {

    @Override
    public Object processBeforeInitializing() {
        return null;
    }

    @Override
    public Object processAfterInitializing() {
        return null;
    }
}
