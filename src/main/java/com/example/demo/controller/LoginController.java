package com.example.demo.controller;

import com.example.demo.dto.SysUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/login")
    public void LoginController(@RequestBody SysUser user) {
        //return Res.success(null);
    }
}
