package com.tydic.udbh.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 项目全局拦截器
 */
@WebFilter(filterName = "myfilter", urlPatterns = "/aa")
public class CasFiter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        //注释掉这行 代码无法向下执行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
//        Filter.super.destroy();
    }
}
