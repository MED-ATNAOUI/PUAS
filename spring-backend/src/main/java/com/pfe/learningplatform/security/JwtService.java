package com.pfe.learningplatform.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =

            "mysecretkeymysecretkeymysecretkey123456";

    /*
     * =========================================
     * SECRET KEY
     * =========================================
     */

    private Key getSignKey() {

        return Keys.hmacShaKeyFor(
                SECRET.getBytes()
        );
    }

    /*
     * =========================================
     * GENERATE TOKEN (avec rôle)
     * =========================================
     */

    public String generateToken(
            String email,
            String role
    ) {

        return Jwts.builder()

                .setSubject(email)

                .claim("role", role)

                .setIssuedAt(
                        new Date()
                )

                .setExpiration(

                        new Date(

                                System.currentTimeMillis()

                                        + 1000 * 60 * 60 * 24
                        )
                )

                .signWith(

                        getSignKey(),

                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    /*
     * =========================================
     * GENERATE TOKEN (sans rôle - rétrocompat)
     * =========================================
     */

    public String generateToken(
            String email
    ) {

        return generateToken(email, "USER");
    }

    /*
     * =========================================
     * EXTRACT EMAIL
     * =========================================
     */

    public String extractEmail(
            String token
    ) {

        return extractAllClaims(token)
                .getSubject();
    }

    /*
     * =========================================
     * EXTRACT ROLE
     * =========================================
     */

    public String extractRole(
            String token
    ) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    /*
     * =========================================
     * EXTRACT CLAIMS
     * =========================================
     */

    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parserBuilder()

                .setSigningKey(
                        getSignKey()
                )

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    /*
     * =========================================
     * CHECK TOKEN EXPIRATION
     * =========================================
     */

    private boolean isTokenExpired(
            String token
    ) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /*
     * =========================================
     * VALIDATE TOKEN
     * =========================================
     */

    public boolean isTokenValid(

            String token,

            String email
    ) {

        return extractEmail(token)
                .equals(email)

                && !isTokenExpired(token);
    }
}