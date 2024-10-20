package com.myproject.eshop.demos.web.Auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

/**
 * Description:
 * Author:
 * Data:
 */
@Component
public class JWT {
    //密钥
    private static final String KEY = "5efc9a";
    //过期时间
    private static final Long timeOut = 1000*60*60*24L;
            //3600000L; // 1 hour

    public static String createToken(String username, int roleid) {
        JwtBuilder builder = Jwts.builder();
        String jwtToken = builder
                //Header
                .setHeaderParam("type", "JWT")
                .setHeaderParam("alg", "HS256")
                //Payload
                .claim("username", username)
                .claim("roleid", roleid)
                .setSubject("user")
                .setExpiration(new Date(System.currentTimeMillis() + timeOut))
                .setId(UUID.randomUUID().toString())
                //Signature
                .signWith(SignatureAlgorithm.HS256,KEY)
                .compact();
        return jwtToken;
    }

    public static Object parseToken(String token) {
        JwtParser jwtParser = Jwts.parser();
        Jws<Claims> claimsJwt = jwtParser.setSigningKey(KEY).parseClaimsJws(token);
        Claims claims = claimsJwt.getBody();
        return claims;

    }

    public static Boolean checkToken(String token) {
        if (token == null) {
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
