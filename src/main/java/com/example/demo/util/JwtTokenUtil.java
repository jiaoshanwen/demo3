package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT Token 工具类
 * 提供生成、解析、验证和刷新 JWT 的方法
 */
@Component
public class JwtTokenUtil {

    // 从配置文件中读取密钥和过期时间
    @Value("${jwt.secret:defaultSecretKeyForJwtSigningMustBeAtLeast32Chars}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 默认 24 小时（毫秒）
    private Long expiration;

    /**
     * 生成签名密钥（基于 HS256）
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 从 Token 中提取用户名（subject）
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从 Token 中提取过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从 Token 中提取指定类型的 Claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 获取 Token 中的所有 Claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查 Token 是否过期
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 生成 Token（使用默认 claims，仅包含 subject）
     */
    public String generateToken(String subject) {
        return generateToken(new HashMap<>(), subject);
    }

    /**
     * 生成 Token（可携带自定义 claims）
     */
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 验证 Token 是否有效（未过期且签名正确）
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            // 签名无效、过期或格式错误均返回 false
            return false;
        }
    }

    /**
     * 刷新 Token（仅当原 Token 未过期时生成新 Token，可扩展）
     */
    public String refreshToken(String token) {
        if (!validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        final Claims claims = getAllClaimsFromToken(token);
        // 保留原 subject 和 claims，重置过期时间
        return generateToken(claims, claims.getSubject());
    }

    /**
     * 从 Token 中获取自定义 Claim（例如 userId）
     */
    public String getClaim(String token, String claimKey) {
        return getAllClaimsFromToken(token).get(claimKey, String.class);
    }
}