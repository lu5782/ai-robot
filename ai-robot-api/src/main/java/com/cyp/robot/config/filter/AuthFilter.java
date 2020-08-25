package com.cyp.robot.config.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;


@Component
public class AuthFilter implements Filter {

    @Value("${jiuyoujueyu.jwt.cookieName}")
    private String cookieName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        String url = servletRequest.getRequestURI();
//        if (url.indexOf("/auth/") == 0) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = null;
//        if (servletRequest.getCookies() != null){
//            for (Cookie cookie : servletRequest.getCookies())
//                if (cookie.getName().equals(cookieName)) {
//                    token = cookie.getValue();
//                    break;
//                }
//        }
//
//        if (token == null) {
//            JwtUtils.jwtError(response);
//            return;
//        }
//
//        if (JwtUtils.verifyJwt(token)) {
//            JwtUtils.createJwt((HttpServletResponse) response);
//        }
        chain.doFilter(request, response);
    }



    @Override
    public void destroy() {
    }

}
