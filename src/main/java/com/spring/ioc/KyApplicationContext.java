package com.spring.ioc;

import com.spring.ioc.marktypes.Component;
import com.spring.ioc.marktypes.Service;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @user KyZhang
 * @date
 */
public class KyApplicationContext {

    Class<?> configClz;
    ConcurrentMap<String,Object> singleBeans = new ConcurrentHashMap();
    ConcurrentMap<String,BeanDefinition> definitions = new ConcurrentHashMap();
    Set<org.springframework.beans.factory.config.BeanPostProcessor> bppSet = new TreeSet<>();

    List<Class<? extends Annotation>> markTypes = new LinkedList(Arrays.asList(Component.class,Service.class));




    /**
     * creating ioc
     * @param configClz
     */
    public KyApplicationContext(Class<?> configClz) {
        this.configClz = configClz;
        //resolve configClz instance --->
        scan(configClz);
        //
        singletonBeansGenerator(definitions);
        System.out.println("ioc is created");


    }

    private void singletonBeansGenerator(ConcurrentMap<String,BeanDefinition> definitions) {
        for (Map.Entry<String,BeanDefinition> entry:definitions.entrySet()) {
            String key = entry.getKey();
            BeanDefinition info = entry.getValue();
            if(!singleBeans.containsKey(key)&&"singleton".equalsIgnoreCase(info.getScope())&&(!info.getaClass().isAnnotationPresent(Lazy.class)||info.getaClass().getDeclaredAnnotation(Lazy.class).value()!=true)){
                //create -- put into beanmap
                Object instance = null;
                try {
                    instance = createBean(key,info.getaClass());


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchBeanException e){
                    e.printStackTrace();
                }

                singleBeans.put(key, instance);

                //bpp为特殊的singleton bean
                if(instance.getClass().isAssignableFrom(BeanPostProcessor.class)){
                    bppSet.add((org.springframework.beans.factory.config.BeanPostProcessor)instance);
                }

            }

        }
    }

    /**
     *
     * create bean by bean_definition
     * @param clz
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private <T> T createBean(String name,Class<T> clz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchBeanException {
        T bean = clz.getDeclaredConstructor().newInstance();

        //@Autowired
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field f:fields) {
            if(f.isAnnotationPresent(Autowired.class)){
                String fieldBeanName = getBeanName(f.getType());
                f.setAccessible(true);
                f.set(bean,getBean(fieldBeanName));
            }
        }

        //Aware callback
        //BeanPostProcessor-- before
        for (org.springframework.beans.factory.config.BeanPostProcessor bpp:bppSet){
            bean = (T)bpp.postProcessBeforeInitialization(bean, name);
        }

        //initializingBean
        //init-method
        //BPP-- after(Aop)

        for (org.springframework.beans.factory.config.BeanPostProcessor bpp:bppSet){
            bean = (T)bpp.postProcessAfterInitialization(bean, name);
        }

        System.out.println("instance named "+name+" is created");

        return bean;
    }

    /**
     * scan "bean".class along the package @Scan.value()
     * definition_map.put(beanName,info)
     * @param configClz
     */
    private void scan(Class<?> configClz) {
        if(configClz.isAnnotationPresent(Scan.class)){
            //可以被spring识别的类<-->配置  ： 有Scan("xx")的注解的
            //to get path : String xx.value()
            String relativePath = configClz.getDeclaredAnnotation(Scan.class).value();
            ClassLoader clzLoader = KyApplicationContext.class.getClassLoader();
            //xxx.xxx.xx --> xxx/xxx/xx   url中用的是'/'
            relativePath = relativePath.replace(".", "/");
            URL url = clzLoader.getResource(relativePath);
            File scanDir = new File(url.getFile());
            //is directory
            if(scanDir.isDirectory()){
                File[] files = scanDir.listFiles();
                for (File f:files){
                    String absolutePath = f.getAbsolutePath();
                    //\diy_spring\target\classes\com\rpc\bean\User.class -->com\rpc\bean\User
                    if(absolutePath.endsWith(".class")){
                        String FQCN = getFQCN(absolutePath);
                        try {
                            Class aClass = clzLoader.loadClass(FQCN);
                            for (Class markClz:markTypes) {
                                if (aClass.isAnnotationPresent(markClz)){
                                    String beanName = getBeanName(aClass);
                                    BeanDefinition info = getDefinition(aClass);
                                    definitions.put(beanName, info);
                                    break;
                                }
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param aClass
     * @return bean_name
     */
    private String getBeanName(Class<?> aClass){

         String beanName = aClass.getSimpleName();

         beanName = (new StringBuilder()).append(Character.toLowerCase(beanName.charAt(0))).append(beanName.substring(1)).toString();

        if(aClass.isAnnotationPresent(Component.class)&&!"".equalsIgnoreCase(aClass.getDeclaredAnnotation(Component.class).value())) {
                beanName = aClass.getDeclaredAnnotation(Component.class).value();
        }

        if(aClass.isAnnotationPresent(Service.class)&&!"".equalsIgnoreCase(aClass.getDeclaredAnnotation(Service.class).value())) {
                beanName = aClass.getDeclaredAnnotation(Service.class).value();
        }

        return beanName;

    }

    /**
     *
     * @param aClass
     * @return bean_information
     */
    private BeanDefinition getDefinition(Class<?> aClass) {
            //  ioc components.....
            BeanDefinition info = new BeanDefinition(aClass);
            if (aClass.isAnnotationPresent(Scope.class)){
                info.setScope(aClass.getDeclaredAnnotation(Scope.class).value());
            }
            return info;
    }


    private String getFQCN(String path){
        path = path.substring(path.indexOf("com"), path.indexOf(".class"));
        return path.replace('\\', '.');  //windows中用的是 '\'
    }



    /**
     * 
     * @param beanName
     * @return  bean instance
     */
    public Object getBean(String beanName) throws NoSuchBeanException {

        if(definitions.containsKey(beanName)){
            try {
                if("singleton".equals(definitions.get(beanName).getScope())){
                    if (!singleBeans.containsKey(beanName)) {
                        Object bean = createBean(beanName, definitions.get(beanName).getaClass());
                        singleBeans.put(beanName, bean);
                    }
                    return singleBeans.get(beanName);
                }else
                    return createBean(beanName, definitions.get(beanName).getaClass());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }else{
            throw new NoSuchBeanException();
        }
         return null;
    }

    /**
     *
     * @param beanName
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T getBean(String beanName, Class<T> clz) throws NoSuchBeanException {
        return (T)getBean(beanName);
    }
}
