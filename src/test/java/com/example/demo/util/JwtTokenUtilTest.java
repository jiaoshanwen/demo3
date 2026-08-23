package com.example.demo.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenUtilTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    public void generateToken() {
        String zs = jwtTokenUtil.generateToken("zs");
        System.out.println(zs);
    }
    //eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6cyIsImlhdCI6MTc4NzQwNDI5NSwiZXhwIjoxNzg3NDkwNjk1fQ.AqrHwLuYB7iW4pQDxnRULMo5pHn4LaUjATt2dxcZzBk

    @Test
    public void verifyToken() {
        Boolean b = jwtTokenUtil.validateToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6cyIsImlhdCI6MTc4NzQwNDI5NSwiZXhwIjoxNzg3NDkwNjk1fQ.AqrHwLuYB7iW4pQDxnRULMo5pHn4LaUjATt2dxcZzBk");
        System.out.println(b);
    }

    @Test
    public void
}
