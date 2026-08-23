package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class SysUser {
    private String username;
    private String password;
    private List<String> roles;
}
