package sunjx.framework.helper;

import sunjx.framework.annotation.Controller;
import sunjx.framework.annotation.Service;
import sunjx.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by sunjx on 2018/1/10.
 */
public final class BeanHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(ClassHelper.getClassByType(Service.class));
        beanClassSet.addAll(ClassHelper.getClassByType(Controller.class));

        for (Class<?> cls : beanClassSet){
            BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
        }

        LOGGER.info("load bean map completed");
    }

    /**
     * 获取bean映射
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取bean实例
     */
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            LOGGER.error("can not get bean by class:" + cls.getName());
            throw new RuntimeException("can not get bean by class:" + cls.getName());
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置bean实例
     */
    public static void putBean(Class<?> cls, Object object){
        BEAN_MAP.put(cls, object);
    }

}
