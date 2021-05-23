package com.rpc.bean;

import com.spring.ioc.Autowired;
import com.spring.ioc.marktypes.Component;

/**
 * @user KyZhang
 * @date
 */
@Component
public class User {

    private String name;
    private Integer age;

    @Autowired
    private Employee emp;

    public User(String name, int age, Employee emp) {
        this.name = name;
        this.age = age;
        this.emp = emp;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", emp=" + emp +
                '}';
    }
}
