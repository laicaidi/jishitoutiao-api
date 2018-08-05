package com.jishitoutiao.filter;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 * @author leitianxiang on 2018/7/3.
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private String SECRET = "FreeJiShiTouTiaoDom";
    private String authHeader = "Authorization";
    private String tokenHead = "Bearer ";

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(authHeader);
        logger.info("doFilterInternal() run, authHeader: " + header);

        if (header == null || !header.startsWith(tokenHead)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(authHeader);

        if (token != null) {
            // 解析token.
            String username = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(tokenHead, ""))
                    .getBody()
                    .get("username").toString();

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}