package com.springboot.socialnetworkapi.security.jwt;

import com.springboot.socialnetworkapi.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtToken {

    private static final Logger logger = LoggerFactory.getLogger(JwtToken.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${accessToken.validity}")
    private int accessTokenValidity;

    public String generateTokenFromUsername(User user){
        return doGenerateToken(user.getUsername());
    }

    public String doGenerateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accessTokenValidity))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());  // khóa không hợp lệ
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());      // thông báo không hợp lệ
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());   // khóa hết hạn
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());   // không được hỗ trợ
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage()); // chuỗi yêu cầu JWT rỗng
        }

        return false;
    }
}
