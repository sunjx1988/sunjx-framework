package sunjx.framework.bean;

import java.lang.reflect.Method;

/**
 * Created by sunjx on 2018/1/10.
 */
public class ActionHandler {

    private Class<?> controllerClass;

    private Method actionMethod;

    public ActionHandler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
