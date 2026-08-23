package com.example.demo.config.handler.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfo {
    private  Long userId;
    private String nickname;
    private String roleId;
    private Long expiredTime;
}
