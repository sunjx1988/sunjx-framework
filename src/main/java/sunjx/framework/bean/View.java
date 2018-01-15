package sunjx.framework.bean;

import java.util.Map;

/**
 * Created by sunjx on 2018/1/10.
 */
public class View {

    private String path;

    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
    }

    public View(String path, Map<String, Object> model) {
        this.path = path;
        this.model = model;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public View addModel(String key , String value){
        model.put(key, value);
        return this;
    }
}
