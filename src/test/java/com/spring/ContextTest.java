package com.spring;

import com.rpc.MyConfig;
import com.rpc.bean.Employee;
import com.rpc.bean.User;
import com.spring.ioc.KyApplicationContext;
import com.spring.ioc.NoSuchBeanException;

/**
 * @user KyZhang
 * @date
 */
public class ContextTest {

    public static void main(String[] args) throws NoSuchBeanException {
        
        KyApplicationContext ioc = new KyApplicationContext(MyConfig.class);
        User user = (User)ioc.getBean("user");
        user.setName("Tom");
        user.setAge(18);
        System.out.println(user);
        Object user2 = ioc.getBean("user");
        System.out.println(user2);
        Employee employee = ioc.getBean("emp", Employee.class);
        System.out.println(employee);
        Employee employee1 = ioc.getBean("emp", Employee.class);
        System.out.println(employee1);

    }

}
