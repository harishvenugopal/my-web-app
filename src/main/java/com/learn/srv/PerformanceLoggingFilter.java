package com.learn.srv;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class PerformanceLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Log Filter active.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        long startTime = System.currentTimeMillis();
        System.out.println("Incoming Request Intercepted before matching Servlet execution.");

        // Hand control execution forward down the pipeline to the next filter or target servlet
        chain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        System.out.println("Outgoing Response Intercepted. Processed in: " + (endTime - startTime) + "ms");
    }

    @Override
    public void destroy() {}
}
