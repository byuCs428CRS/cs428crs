package controllers;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by nhumrich on 3/11/14.
 */
public class ApiTrafficLoggingFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //ToDo: add logger here
    }

    @Override
    public void destroy() {

    }
}
