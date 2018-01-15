package sunjx.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by sunjx on 2018/1/11.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
