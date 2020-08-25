package com.cyp.robot.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    private static String issuer;
    private static String subject;
    private static String expireTime;
    private static String secretKey;
    private static String cookieName;
    private static String staffId;
    private static String ownerId;
    private static final String errorType = "application/json; charset=UTF-8";
    private static final String errorMsg = "{\"errorCode\":401,\"errorMessage\":\"Invalid access token or token expired\"}";

    public static void createJwt(HttpServletResponse response) {
        Claims cs = Jwts.claims();
        cs.put(staffId, "staffInfo.getStaffId()");
        cs.put(ownerId, "staffInfo.getOwnerId()");

        String token = Jwts.builder()
                .setClaims(cs)
                .setSubject(subject)
                .setIssuer(issuer)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expireTime)))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        log.info("生成token");
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.parseInt(expireTime) / 1000);
        response.addCookie(cookie);
    }

    public static boolean verifyJwt(String token) {
        Claims body;
        try {
            body = Jwts.parser()
                    .requireSubject(subject)
                    .requireIssuer(issuer)
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = body.getExpiration();
            if (new Date().getTime() > expiration.getTime()) {
                throw new RuntimeException("登录超时");
            }
            String newToken = Jwts.builder()
                    .setClaims(body)
                    .signWith(SignatureAlgorithm.HS512, secretKey).compact();
            return token.equals(newToken);
        } catch (Exception e) {
            return false;
        }
    }

    public static void jwtError(ServletResponse response) throws IOException {
        ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(errorType);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(errorMsg);
        }
    }

    @Value("${jiuyoujueyu.jwt.issuer}")
    public void setIssuer(String issuer) {
        JwtUtils.issuer = issuer;
    }

    @Value("${jiuyoujueyu.jwt.subject}")
    public void setSubject(String subject) {
        JwtUtils.subject = subject;
    }

    @Value("${jiuyoujueyu.jwt.logOutTime}")
    public void setExpireTime(String expireTime) {
        JwtUtils.expireTime = expireTime;
    }

    @Value("${jiuyoujueyu.jwt.secretKey}")
    public void setSecretKey(String secretKey) {
        JwtUtils.secretKey = secretKey;
    }

    @Value("${jiuyoujueyu.jwt.cookieName}")
    public void setCookieName(String cookieName) {
        JwtUtils.cookieName = cookieName;
    }

    @Value("${jiuyoujueyu.jwt.dataKey.staffId}")
    public void setStaffId(String staffId) {
        JwtUtils.staffId = staffId;
    }

    @Value("${jiuyoujueyu.jwt.dataKey.ownerId}")
    public void setOwnerId(String ownerId) {
        JwtUtils.ownerId = ownerId;
    }
}
