package com.example.backend.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "Carlos Antunez 2229";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public static String createToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(ALGORITHM);
    }

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    public static String extractEmailFromJwt(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public static String getJwtFromCookies(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    try {
                        String token = cookie.getValue();
                        DecodedJWT decodedJWT = verifyToken(token);
                        return decodedJWT.getToken();
                    } catch (JWTVerificationException e) {
                        throw new JWTVerificationException(("Invalid JWT"));
                    }
                }
            }
        }
        throw new JWTVerificationException("JWT cookie not found");
    }

}