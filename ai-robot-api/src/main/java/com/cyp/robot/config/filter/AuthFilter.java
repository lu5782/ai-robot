package com.cyp.robot.config.filter;

import com.cyp.robot.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Component
public class AuthFilter implements Filter {

    @Value("${jwt.cookieName}")
    private String cookieName;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private static ArrayList<String> excludeList = new ArrayList<>();

    static {
        excludeList.add("/");
        excludeList.add("/auth");
        excludeList.add("/nas");
        excludeList.add("/nas/upload");
        excludeList.add("/nas/getChild");
        excludeList.add("/nas/upload");
        excludeList.add("/download");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        String requestURI = servletRequest.getRequestURI();
//        if (excludeList.contains(requestURI) || requestURI.endsWith(".css") || requestURI.endsWith(".js")
//                || requestURI.startsWith("/fonts") || requestURI.startsWith("/frame")
//                || requestURI.startsWith("/favicon") || requestURI.startsWith("/libs")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        String token = null;
//        if (servletRequest.getCookies() != null) {
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
