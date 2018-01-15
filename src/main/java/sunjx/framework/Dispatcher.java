package sunjx.framework;

import sunjx.framework.bean.ActionHandler;
import sunjx.framework.bean.Data;
import sunjx.framework.bean.Param;
import sunjx.framework.bean.View;
import sunjx.framework.helper.BeanHelper;
import sunjx.framework.helper.ConfigHelper;
import sunjx.framework.helper.ControllerHelper;
import sunjx.framework.util.CodecUtil;
import sunjx.framework.util.JsonUtil;
import sunjx.framework.util.ReflectionUtil;
import sunjx.framework.util.StreamUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunjx on 2018/1/10.
 */
public class Dispatcher extends HttpServlet{

    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化helper相关类
        HelperLoader.init();
        LOGGER.info("Dispatch init completed");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求方法和请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        String requestUrl = req.getRequestURI();
        String contextPath = req.getContextPath();

        requestUrl = requestUrl.substring(contextPath.length());

        if(requestUrl.indexOf("?") > 0)
            requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));

        LOGGER.info("requestMethod:"+requestMethod+", requestPath:" + requestPath + ",contextPath:" + contextPath + ", requestUrl:" + requestUrl);

        //获取action处理器
        ActionHandler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if(handler != null){
            //获取controller和bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();

            while (paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            //解析参数
            String body = CodecUtil.decodeUrl(StreamUtil.getString(req.getInputStream()));

            if(StringUtils.isNotEmpty(body)){
                String[] params = body.split("&");

                if(ArrayUtils.isNotEmpty(params)){
                    for(String param : params){
                        String[] array = param.split("=");
                        if(ArrayUtils.isNotEmpty(array)){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            //调用action方法
            Method actionMethod = handler.getActionMethod();
            Object result;

            //判断是否有参数
            if(paramMap.isEmpty()){
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            }else{
                Param param = new Param(paramMap);
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }

            //处理action方法返回值
            if(result instanceof View){
                //返回jsp页面
                View view = (View) result;
                String path = view.getPath();

                if(StringUtils.isNotEmpty(path)){

                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + path);
                    }else {
                        Map<String ,Object> model = view.getModel();

                        //判断返回数据是否为空
                        if(model != null){
                            for(Map.Entry<String ,Object> entry : model.entrySet()){
                                req.setAttribute(entry.getKey(), entry.getValue());
                            }
                        }

                        LOGGER.info("target page:" + ConfigHelper.getAppJspPath() + path);
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }

                }

            }else if(result instanceof Data){
                //返回json
                Data data = (Data) result;
                Object model = data.getModel();

                if(model != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }

        }else {
            resp.sendError(400);
        }
    }


}
