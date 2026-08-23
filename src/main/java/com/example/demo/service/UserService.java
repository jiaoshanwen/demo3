package com.example.demo.service;

import com.example.demo.dto.SysUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    public SysUser findFromDB(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setPassword("zs");
        sysUser.setPassword("$2a$10$H47a6134lLUTbd2V9aQnpu.Y5jEO4B4d4oT1VMtlM8b9KqFLmcmWe");
        ArrayList<String> list = new ArrayList<>();
        list.add("admin");
        list.add("user");
        sysUser.setRoles(list);
        return sysUser;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
}
