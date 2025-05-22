package com.ganesh.blog.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    // JWT Token validity (1 hour)
    public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000L; // 1 hour validity

    // Load secret key (should be 32 bytes long for HS256)
    private final Key secretKey = Keys.hmacShaKeyFor("yourSuperSecureSecretKeyMustBe32BytesLong".getBytes());

    // Generate JWT Token
    public String generateToken(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty for token generation");
        }

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Extract a claim from the token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    // Retrieve all claims from JWT token using secret key
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("❌ Token Expired: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("❌ Invalid Token: " + e.getMessage());
        } catch (SignatureException e) {
            System.out.println("❌ Invalid Signature: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error Parsing Token: " + e.getMessage());
        }
        return null;
    }

    // Check if token has expired
    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    // Validate JWT token using UserDetails
    public boolean validateToken(String token, UserDetails userDetails) {
        String tokenUsername = getUsernameFromToken(token);
        String providedUsername = userDetails.getUsername(); // Correct usage

        // Logging for debugging
        System.out.println("✅ Extracted Username from Token: " + tokenUsername);
        System.out.println("✅ Provided Username for Validation: " + providedUsername);
        System.out.println("⏰ Token Expiration: " + getExpirationDateFromToken(token));
        System.out.println("⏰ Is Expired: " + isTokenExpired(token));

        if (tokenUsername == null || providedUsername == null) {
            System.out.println("❌ Validation failed: Null values found");
            return false;
        }

        // Validate token based on username and expiration
        boolean isValid = tokenUsername.trim().equalsIgnoreCase(providedUsername.trim()) && !isTokenExpired(token);
        System.out.println("✅ Is token valid? " + isValid);

        return isValid;
    }
}
