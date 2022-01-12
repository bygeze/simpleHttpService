package com.example.simpleHttpService.filters;


import org.springframework.stereotype.Component;
 
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ITAcademyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                        ServletResponse response,
                        FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
 
        res.addHeader("IT-Academy-Excercise", "Simple-Service");
        chain.doFilter(req, res);
    }
}
