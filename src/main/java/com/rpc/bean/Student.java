package com.rpc.bean;

import com.spring.ioc.Lazy;
import com.spring.ioc.marktypes.Service;

/**
 * @user KyZhang
 * @date
 */
@Service
@Lazy
public class Student {

    private String name;
    private Integer age;

    public Student() {
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



}
