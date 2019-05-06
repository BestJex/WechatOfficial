package com.jex.official.common;

import com.jex.official.entity.db.AdminUser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute("adminUser");
        if (adminUser == null) {
            boolean isAjaxRequest = false;
            if(StringUtils.hasText(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
                isAjaxRequest = true;
            }
            if(isAjaxRequest) {
                String message = "{\"message\":\"登录已失效，请刷新页面或重新登录！\",\"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(message);
                writer.close();
                response.flushBuffer();
            } else {
                response.sendRedirect("/admin/noSession");
            }
           return false;
        } else {
           return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
