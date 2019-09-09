package com.itlike.web.filter;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyFilter extends StrutsPrepareAndExecuteFilter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        //获取当前的请求
        HttpServletRequest request = (HttpServletRequest)req;
        //获取请求地址来进行判断
        String requestURI = request.getRequestURI();
        if(requestURI.contains("js/umedit/jsp/controller.jsp")){
            //放行
            chain.doFilter(req,res);
        }else {
            super.doFilter(req,res,chain);
        }
    }
}
