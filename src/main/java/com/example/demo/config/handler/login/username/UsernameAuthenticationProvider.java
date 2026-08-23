package com.example.demo.config.handler.login.username;

import com.alibaba.fastjson2.JSON;
import com.example.demo.dto.SysUser;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String credentials = (String)authentication.getCredentials();
        System.out.println("name: " + name + " credentials: " + credentials);

        SysUser user = userService.findFromDB(name);

        if(user==null || !passwordEncoder.matches(credentials, user.getPassword())) {
            throw  new BadCredentialsException("用户名或者密码不正确");
        }

        UsernameAuthentication usernameAuthentication = new UsernameAuthentication();
       // usernameAuthentication.setCurentUser(JSON);
        usernameAuthentication.setAuthenticated(true);
        return usernameAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernameAuthentication.class);
    }
}
