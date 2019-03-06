package xin.zhangshuo.grape.example.controller;

import xin.zhangshuo.grape.core.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public class UserController {

    /**
     * Example
     * @param request 你必须添加HttpServletRequest 因为反射执行时需要调用
     * @return
     *
     * 视图解析器 path = "/WEB-INF/jsp/" + path + ".jsp"
     * 你必须将jsp页面放在/WEB-INF/jsp目录下
     */
    @RequestMapping("/login.do")
    public String login(HttpServletRequest request){
        return "login";
    }
}
