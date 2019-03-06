package xin.zhangshuo.grape.core;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 处理器
 */
public class Handler {
    /**
     * 子控制器对象
     */
    private Object controller;
    /**
     * 子控制的方法
     */
    private Method method;

    public Handler() {
    }

    public Handler(Object controller, Method method) {
        this.controller = controller;
        this.method = method;
    }

    @Override
    public String toString() {
        return "Handler [controller=" + controller + ", method=" + method + "]";
    }

    public String execute(HttpServletRequest req) throws Exception {
        return (String) method.invoke(controller, req);
    }

}





