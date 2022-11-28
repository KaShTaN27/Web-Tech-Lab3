package by.bsuir.lab3.token;

import by.bsuir.lab3.exception.InvalidTokenException;
import by.bsuir.lab3.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretWord;

    @Value("${jwt.expirationTime}")
    private int expirationTime;

    public String generateToken(String username, Collection<Role> roles) {
        return compactToken(setClaims(username, roles));
    }

    private String compactToken(Claims claims) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(HS256, secretWord)
                .compact();
    }

    private Claims setClaims(String username, Collection<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        List<String> roleNames = new ArrayList<>();
        roles.forEach(role -> roleNames.add(role.name()));
        claims.put("roles", roleNames);
        return claims;
    }

    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("JWT token is invalid");
        }
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
