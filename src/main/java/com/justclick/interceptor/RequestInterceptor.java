package com.justclick.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();
        
        String tenantID = request.getHeader("X-TenantID") == null ? "public" : request.getHeader("X-TenantID");
        
        System.out.println("RequestURI::" + requestURI +" || Search for X-TenantID  :: " + tenantID);
        System.out.println("____________________________________________");
        /*if (tenantID == null) {
        	TenantContext.setCurrentTenant("public");
            return true;
        }*/
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }

}

