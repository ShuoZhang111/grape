package xin.zhangshuo.grape.core;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * 前端控制器，处理所有指定的请求请在web.xml中使用<servlet></servlet>指定
 *
 * 例如：
 <servlet>
   <servlet-name>DispatchServlet</servlet-name>
   <servlet-class>xin.zhangshuo.group.core.DispatchServlet</servlet-class>
   <init-param>
     <param-name>config</param-name>
     <param-value>mvc.xml</param-value>
   </init-param>
   <load-on-startup>1</load-on-startup>
 </servlet>

 <servlet-mapping>
   <servlet-name>DispatchServlet</servlet-name>
   <url-pattern>*.do</url-pattern>
 </servlet-mapping>
 *推荐使用<load-on-startup>1</load-on-startup>指定该类在容器启动时执行init
 * 前端控制器处理全部的Web功能
 **/
public class DispatchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private HandlerMapping handlerMapping;

    @Override
    public void init() throws ServletException {
        try {
            handlerMapping = new HandlerMapping();

            SAXReader reader = new SAXReader();
            String file = getInitParameter("config");
            InputStream in = getClass().getClassLoader().getResourceAsStream(file);
            Document doc = reader.read(in);
            Element root = doc.getRootElement();
            List<Element> list = root.elements("bean");

            for (Element element : list) {
                String className = element.attributeValue("class");
                handlerMapping.parseController(className);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("控制器解析错误", e);
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        try {

            String path = request.getRequestURI();
            String contextPath = request.getContextPath();
            path = path.substring(contextPath.length());
            Handler handler = handlerMapping.get(path);
            path = handler.execute(request);

            if (path.startsWith("redirect:")) {
                path = path.substring("redirect:".length());
                if (path.startsWith("http")) {
                    response.sendRedirect(path);
                } else {
                    path = contextPath + path;
                    response.sendRedirect(path);
                }
            } else {
                path = "/WEB-INF/jsp/" + path + ".jsp";
                request.getRequestDispatcher(path).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("系统故障:" + e.getMessage());
        }
    }
}






