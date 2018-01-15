package sunjx.framework.helper;

import sunjx.framework.ConfigConstant;
import sunjx.framework.util.PropsUtil;
import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * Created by sunjx on 2018/1/9.
 */
public final class ConfigHelper {

    private static final Properties PROPERTIES_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(PROPERTIES_PROPS, ConfigConstant.APP_BASE_PACKAGE, "");
    }

    /**
     * 获取jsp页面路径
     */
    public static String getAppJspPath(){
        String jspPath = PropsUtil.getString(PROPERTIES_PROPS, ConfigConstant.APP_JSP_PATH, "");
        if(!StringUtils.endsWith(jspPath,"/")){
            jspPath += "/";
        }
        return jspPath;
    }
}
