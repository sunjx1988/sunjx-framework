package sunjx.business.aop;


import sunjx.framework.annotation.Aspect;
import sunjx.framework.annotation.Controller;
import sunjx.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by sunjx on 2018/1/11.
 */
@Aspect(Controller.class)
public class Log extends AspectProxy{

    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

    @Override
    public void begin() {
        LOGGER.info("Log begin ...");
    }

    @Override
    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.info("Log intercept ...");
        return true;
    }

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.info("Log before ...");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.info("Log after ...");
    }

    @Override
    public void error(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.info("Log error ...");
    }

    @Override
    public void end() {
        LOGGER.info("Log end ...");
    }
}
