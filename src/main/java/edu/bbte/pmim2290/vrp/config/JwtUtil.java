package edu.bbte.pmim2290.vrp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String JWT_SECRET = "HajraSportklubCsikszeredaMarkMarkMarkMarkMarkMark";
    private static long expiration = 1000L * 60 * 60 * 10; // 10 hours

    public String generateToken(final String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public String extractUsername(final String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(final String token, final String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(final String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
