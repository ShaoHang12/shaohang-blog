package com.xsh.blog.common.utils;


import com.xsh.blog.common.bean.ApiCode;
import com.xsh.blog.common.excetion.ErrorRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author root
 */
@Component
public class JwtUtil {

    static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static String password = "123456";
    /**
     * 有效期为2个小时
     */
    private static int validPeriod = 86400;//秒


    /**
     * 生成token
     *
     * @param username 用户名
     * @return 结果Interceptor
     */
    public static String createTempToken(String username) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + (validPeriod * 1000)))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, password);
        return jwtBuilder.compact();
    }

    /**
     * 生成token
     *
     * @param username 用户名
     * @return 结果Interceptor
     */
    public static String getToken(String username) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + (validPeriod * 1000)))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, password);
        return jwtBuilder.compact();
    }

    /**
     * 校验token是否正确
     *
     * @param token token
     * @return true/false
     */
    public static boolean checkToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(password)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {

            throw new ErrorRequestException(ApiCode.LOGIN_EXPIRED);
        }
    }


    /**
     * 根据token获取用户名
     *
     * @param token token
     * @return 用户名
     */
    public static String getUsername(String token) {
        Claims claims;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(password)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            throw new ErrorRequestException(ApiCode.JWTDECODE_EXCEPTION);
        }
    }

}
