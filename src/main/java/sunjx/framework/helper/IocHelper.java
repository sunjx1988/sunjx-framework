package sunjx.framework.helper;

import sunjx.framework.annotation.Inject;
import sunjx.framework.util.ReflectionUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunjx.framework.util.CollectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by sunjx on 2018/1/10.
 */
public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        Map<Class<?> , Object> beanMap = BeanHelper.getBeanMap();

        if(!CollectionUtil.isEmpty(beanMap)){
            for(Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanObject = beanEntry.getValue();

                Field[] beanFields = beanClass.getDeclaredFields();

                if(ArrayUtils.isNotEmpty(beanFields)){
                    for (Field field: beanFields){
                        if(field.isAnnotationPresent(Inject.class)){
                            Class<?> filedClass = field.getType();
                            Object beanFieldInstance = beanMap.get(filedClass);
                            if(beanFieldInstance != null){
                                ReflectionUtil.setField(beanObject, field, beanFieldInstance);
                                LOGGER.info("set field bean field {Class:"+beanClass.getName()+", Field:"+field.getName()+"} instance");
                            }else {
                                LOGGER.error("can not [inject] bean field {Class:"+beanClass.getName()+", Field:"+field.getName()+"} instance");
                            }
                        }
                    }
                }

            }
            LOGGER.info("inject completed");
        }
    }


}
