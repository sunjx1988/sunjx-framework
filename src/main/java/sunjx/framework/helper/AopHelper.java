package sunjx.framework.helper;

import sunjx.framework.annotation.Aspect;
import sunjx.framework.proxy.AspectProxy;
import sunjx.framework.proxy.Proxy;
import sunjx.framework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunjx.framework.util.CollectionUtil;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by sunjx on 2018/1/11.
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    /**
     * 静态方法初始化
     */
    static {
        try {
            //创建代理类与目标类集合映射
            Map<Class<?>, Set<Class<?>>> proxyMap = creatProxyMap();
            //创建目标类与代理类集合映射
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);

            //创建代理对象，并加入bean容器
            if (!CollectionUtil.isEmpty(targetMap)) {
                for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                    Class<?> targetClass = targetEntry.getKey();
                    List<Proxy> proxyList = targetEntry.getValue();
                    //创建代理对象
                    Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                    BeanHelper.putBean(targetClass, proxy);
                }

                if(LOGGER.isInfoEnabled()){
                    for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                        Class<?> targetClass = targetEntry.getKey();
                        List<Proxy> proxyList = targetEntry.getValue();
                        LOGGER.info("target Class:" + targetClass.getName());
                        if (!CollectionUtil.isEmpty(proxyList)) {
                            LOGGER.info("proxy list : {");
                            for (Proxy proxy : proxyList) {
                                LOGGER.info(proxy.getClass().getName());
                            }
                            LOGGER.info("}");
                        }
                    }
                }

                LOGGER.info("create proxy object and put beanSet completed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建代理类与目标类集合映射
     */
    private static Map<Class<?>, Set<Class<?>>> creatProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassBySuperType(AspectProxy.class);

        if (!CollectionUtil.isEmpty(proxyClassSet)) {
            for (Class<?> proxyClass : proxyClassSet) {
                if (proxyClass.isAnnotationPresent(Aspect.class)) {
                    Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                    Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                    proxyMap.put(proxyClass, targetClassSet);
                }
            }
        }

        return proxyMap;
    }

    /**
     * 创建目标类集合
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassByType(annotation));
        }
        return targetClassSet;
    }

    /**
     * 创建目标类与代理类集合映射
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();

        if (!CollectionUtil.isEmpty(proxyMap)) {
            for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
                Class<?> proxyClass = proxyEntry.getKey();
                Set<Class<?>> targetClassSet = proxyEntry.getValue();

                if (!CollectionUtil.isEmpty(targetClassSet)) {
                    for (Class<?> targetClass : targetClassSet) {

                        //创建代理类
                        Proxy proxy = (Proxy) proxyClass.newInstance();
                        if (!targetMap.containsKey(targetClass)) {
                            List<Proxy> proxyList = new ArrayList<>();
                            targetMap.put(targetClass, proxyList);
                        }
                        targetMap.get(targetClass).add(proxy);
                    }
                }
            }
        }

        return targetMap;
    }

}
