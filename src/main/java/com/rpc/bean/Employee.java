package com.rpc.bean;

import com.spring.ioc.Scope;
import com.spring.ioc.marktypes.Service;

import java.util.Date;

/**
 * @user KyZhang
 * @date
 */
@Service("emp")
@Scope("prototype")
public class Employee {

    private Date in;
    private String name;
    private Character sex;
    private Double salary;

    public Employee() {
    }

    public Employee(Date in, String name, Character sex, Double salary) {
        this.in = in;
        this.name = name;
        this.sex = sex;
        this.salary = salary;
    }

    public Date getIn() {
        return in;
    }

    public void setIn(Date in) {
        this.in = in;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


//    @Override
//    public String toString() {
//        return "Employee{" +
//                "in=" + in +
//                ", name='" + name + '\'' +
//                ", sex=" + sex +
//                ", salary=" + salary +
//                '}';
//    }
}
