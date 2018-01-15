package sunjx.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by sunjx on 2018/1/10.
 */
public final class CodecUtil {

    /**
     * 将URL编码
     */
    public static String encodeUrl(String source){
        String taget = null;
        try {
            taget = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return taget;
    }

    /**
     * 将URL解码
     */
    public static String decodeUrl(String source){
        String taget = null;
        try {
            taget = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return taget;
    }
}
