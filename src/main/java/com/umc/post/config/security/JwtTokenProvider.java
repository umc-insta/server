package com.umc.post.config.security;

import io.jsonwebtoken.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
// @RequiredArgsConstructor
public class JwtTokenProvider {

   // private final JwtProperties jwtProperties;
    private final String secretKey;
    private final String issuer;

    public JwtTokenProvider(@Value("${jwt.secret}") final String secretKey, @Value("${jwt.issuer}") final String issuer) {
        this.secretKey = secretKey;
        this.issuer = issuer;
    }

    public TokenInfo generateToken(Authentication authentication) {

        long now = (new Date()).getTime();
        Date iatTime = new Date();

        System.out.println(authentication);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println("auth " + authorities);

        Date accessTokenExpiration = new Date(now + 3600000); // 1h
        Date refreshTokenExpiration = new Date(now + 1209600000); // 14d

        // Generate Access Token
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(iatTime)
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiration)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        // Generate Refresh Token
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setGrantType("Bearer");
        tokenInfo.setAccessToken(accessToken);
        tokenInfo.setRefreshToken(refreshToken);

        return tokenInfo;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        System.out.println("log" + claims);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT Token" + e);
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token" + e);
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT Token" + e);
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty." + e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}