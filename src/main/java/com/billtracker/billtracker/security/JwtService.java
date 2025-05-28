package com.billtracker.billtracker.security;

import com.billtracker.billtracker.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  private static final String SECRET = "my-super-secret-key-which-should-be-at-least-256-bits-long!";

  private final long EXPIRATION_TIME = 1000 * 60 * 60;

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getId())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJwt(token)
        .getBody()
        .getSubject();
  }

  public boolean isTokenValid(String token, User user) {
    String userId = extractToken(token);
    return userId.equals(user.getId()) && !isExpired(token);
  }

  private boolean isExpired(String token) {
    Date expiration = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parseClaimsJwt(token)
        .getBody()
        .getExpiration();

    return expiration.before(new Date());
  }
}
