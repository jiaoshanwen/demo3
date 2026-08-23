package com.example.demo.config.handler.login.username;

import com.example.demo.config.handler.login.UserLoginInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UsernameAuthentication extends AbstractAuthenticationToken {
    private String username;
    private String password;
    /**
     * 放入jwt的信息
     */
    private UserLoginInfo curentUser;

    public UsernameAuthentication() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return isAuthenticated()?null:password;
    }

    @Override
    public Object getPrincipal() {
        return isAuthenticated()?curentUser:null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserLoginInfo getCurentUser() {
        return curentUser;
    }

    public void setCurentUser(UserLoginInfo curentUser) {
        this.curentUser = curentUser;
    }


}
