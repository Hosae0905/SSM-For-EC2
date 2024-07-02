package com.member.memberservice.adapter.out.jwt;

import com.member.memberservice.application.port.out.LoginMemberJwtOutPort;
import com.member.memberservice.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider implements LoginMemberJwtOutPort {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    private Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    public String generateAccessToken(Member member) {
        Claims claims = Jwts.claims();
        claims.put("memberIdx", member.getMemberIdx());
        claims.put("memberEmail", member.getMemberEmail());

        byte[] secretBytes = secretKey.getBytes();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(Keys.hmacShaKeyFor(secretBytes), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    @Override
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error(e.toString());
        }
        return false;
    }
}
