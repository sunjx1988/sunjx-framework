package sunjx.framework.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by sunjx on 2018/1/12.
 */
public final class CollectionUtil {

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
