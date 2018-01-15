package sunjx.framework.helper;

import sunjx.framework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sunjx on 2018/1/9.
 */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下所有类
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 根据class获取类
     */
    public static Set<Class<?>> getClassByType(Class clz){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(clz)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 根据super class获取类
     */
    public static Set<Class<?>> getClassBySuperType(Class superClass){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET){
            if(superClass.isAssignableFrom(cls) && !cls.equals(superClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
