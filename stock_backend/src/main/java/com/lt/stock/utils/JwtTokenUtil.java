package com.lt.stock.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: ~Teng~
 * @date: 2023/2/14 17:04
 */
public class JwtTokenUtil {
    /**
     * Token 请求头
     */
    public static final String TOKEN_HEADER = "authorization";

    /**
     * Token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 签名主题
     */
    public static final String SUBJECT = "SYSTEM";

    /**
     * 过期时间 默认1天
     */
    public static final long EXPIRATION = 1000 * 60 * 60 * 24L;

    /**
     * 应用密钥
     */
    public static final String APP_SECRET_KEY = "muziteng";

    /**
     * 生成 token
     */
    public static String createToken(String username, String role) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("username", username);
        map.put("role", role);
        return Jwts.builder()
                .setSubject(SUBJECT)
                .addClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, APP_SECRET_KEY)
                .compact();
    }

    /**
     * 校验 token
     */
    public static Claims checkJWT(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(APP_SECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从 token 中获取用户名
     */
    public static String getUsername(String token) {
        return checkJWT(token).get("username").toString();
    }

    /**
     * 校验Token是否过期
     */
    public static String getUserRole(String token) {
        return checkJWT(token).get("role").toString();
    }

    /**
     * 校验Token是否过期
     */
    public static boolean isExpiration(String token) {
        return checkJWT(token).getExpiration().before(new Date());
    }
}
