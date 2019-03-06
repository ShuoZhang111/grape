package xin.zhangshuo.grape.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * HandlerMapping内部维护了一个用于管理 URL 与 子控制器方法的映射关系的HashMap
 */
public class HandlerMapping {
    private Map<String, Handler> mapping = new HashMap<String, Handler>();

    public HandlerMapping() {
    }

    /**
     * 将控制器类找到并添加到map中
     * @param className
     * @throws Exception
     */
    public void parseController(String className) throws Exception {

        Class cls = Class.forName(className);
        Object controller = cls.newInstance();
        Method[] methods = cls.getDeclaredMethods();

        for (Method method : methods) {
            RequestMapping ann = method.getAnnotation(RequestMapping.class);
            if (ann != null) {
                String url = ann.value();
                mapping.put(url, new Handler(controller, method));
            }
        }
    }


    @Override
    public String toString() {
        return "HandlerMapping [mapping=" + mapping + "]";
    }

    /**
     * 根据请求的路径url，找到Handler对象
     * @param url
     * @return
     */
    public Handler get(String url) {
        return mapping.get(url);
    }

}








