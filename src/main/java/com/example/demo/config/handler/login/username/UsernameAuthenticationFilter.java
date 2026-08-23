package com.example.demo.config.handler.login.username;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UsernameAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger log = Logger.getLogger(UsernameAuthenticationFilter.class.getName());
    public UsernameAuthenticationFilter(AntPathRequestMatcher antPathRequestMatcher,
                                           AuthenticationManager authenticationManager,
                                           AuthenticationSuccessHandler authenticationSuccessHandler,
                                           AuthenticationFailureHandler authenticationFailureHandler) {
        super(antPathRequestMatcher);//url 对应postUrl请求
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request
            , HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("use UsernameAuthenticationFilter");

        String requestJsonData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject parse = JSON.parseObject(requestJsonData);
        String username = parse.getString("username");
        String password = parse.getString("password");
        //封装spring security 需要的实体类
        UsernameAuthentication usernameAuthentication = new UsernameAuthentication();
        usernameAuthentication.setUsername(username);
        usernameAuthentication.setPassword(password);
        usernameAuthentication.setAuthenticated(false);
        return getAuthenticationManager().authenticate(usernameAuthentication);
    }
}
