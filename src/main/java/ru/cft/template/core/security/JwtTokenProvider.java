package ru.cft.template.core.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.cft.template.core.model.User;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {
    long jwtExpirationMs = 300000;

    public String generateToken(User user) {
        return Jwts.builder()
                .subject((user.getLastName()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Key getSecretKey(){
        byte[] keyBytes = Decoders.BASE64.decode("MySuperLongSecretKeyThatIsMoreThan32CharsMySuperLongSecretKeyThatIsMoreThan32CharsMySuperLongSecretKeyThatIsMoreThan32Chars");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public long getExpirationTime() {
        return jwtExpirationMs;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException | SignatureException e) {
            return true;
        }    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}