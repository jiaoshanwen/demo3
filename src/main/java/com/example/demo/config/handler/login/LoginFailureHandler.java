package com.example.demo.config.handler.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // 设置响应状态码 401（未授权）
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        // 构建错误响应
        Map<String, Object> result = new HashMap<>();
        result.put("code", 401);
        result.put("message", "登录失败：" + exception.getMessage());
        // 可根据异常类型区分提示信息
        // if (exception instanceof BadCredentialsException) {
        //     result.put("message", "用户名或密码错误");
        // } else if (exception instanceof DisabledException) {
        //     result.put("message", "账号已被禁用");
        // } // ...

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}