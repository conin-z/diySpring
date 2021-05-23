package com.spring.ioc;

/**
 * @user KyZhang
 * @date
 */
public class BeanDefinition {

    private String scope = "singleton";
    private Class<?> aClass;

    public BeanDefinition() {
    }

    public BeanDefinition(Class<?> aClass) {
        this.aClass = aClass;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public Class<?> getaClass() {
        return aClass;
    }
}
