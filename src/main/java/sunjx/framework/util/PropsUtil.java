package sunjx.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sunjx on 2018/1/9.
 */
public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream inputStream = null;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (inputStream == null ){
                throw new FileNotFoundException(fileName + " file is not found");
            }

            props = new Properties();
            props.load(inputStream);

            LOGGER.info("load file [framework.properties] success");
        } catch (FileNotFoundException e) {
            LOGGER.error(fileName + " file is not found");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("load properties file is failure" ,e);
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                    e.printStackTrace();
                }
            }
        }


        if(LOGGER.isInfoEnabled()){
            for (Object key : props.keySet()){
                LOGGER.info((String) key + "=" + props.getProperty((String) key));
            }
        }

        return props;
    }

    public static String getString(Properties props, String key , String defaultValue){
        if(props.containsKey(key)){
            return props.getProperty(key);
        }
        return defaultValue;
    }
}
