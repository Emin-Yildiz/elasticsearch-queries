package com.example.gatewayservice.util;

import com.example.gatewayservice.model.JwtBody;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;


import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    // Haeder'dan jwt'yi çekmek için
    public String getJwtByAuthorizationHeader(HttpHeaders requestHeader){
        String authorizationHeader = requestHeader.get(HttpHeaders.AUTHORIZATION).get(0);
        return authorizationHeader.replace("Bearer ", "");
    }

    // Jwt parse etme ve token içindeki bilgileri kullanarak JwtBody nesnesi oluşturma
    public JwtBody isJwtValid(String jwt) {
        boolean isValid = true;
        String subject = null;
        String userId = "";
        Claims jwtClaims = null;
        String mail = "";
        String role = "";
        try {
            jwtClaims = extractAllClaims(jwt);
            subject = jwtClaims.getSubject();
            userId = String.valueOf(jwtClaims.get("userId"));
            mail = String.valueOf(jwtClaims.get("email"));
            role = String.valueOf(jwtClaims.get("role"));
        } catch (Exception ex) {
            isValid = false;
        }

        if (subject == null || subject.isEmpty()) isValid = false;
        return new JwtBody(userId, mail, role, subject, isValid);
    }

    public Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

}
