package sunjx.framework.bean;

import java.util.Map;

/**
 * Created by sunjx on 2018/1/10.
 */
public class Param {

    private Map<String , Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
