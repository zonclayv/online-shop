package by.haidash.shop.jwt.provider;

import by.haidash.shop.jwt.configuration.JwtConfiguration;
import by.haidash.shop.jwt.exception.WrongAuthenticationTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public JwtTokenProvider(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public Optional<Long> getUserId(HttpServletRequest request) {

        String header = request.getHeader(jwtConfiguration.getHeader());
        if(header == null || !header.startsWith(jwtConfiguration.getPrefix())) {
            return Optional.empty();
        }

        String token = header.replace(jwtConfiguration.getPrefix(), "");

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfiguration.getPublicKey().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.ofNullable(claims.get("userId", Long.class));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String createToken(String username, Long userId, List<String> authorities) {

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .claim("userId", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfiguration.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, jwtConfiguration.getSecretKey().getBytes())
                .compact();

        return jwtConfiguration.getPrefix() + token;
    }

    public Claims resolveToken(HttpServletRequest request) {

        String header = request.getHeader(jwtConfiguration.getHeader());
        if (header == null || !header.startsWith(jwtConfiguration.getPrefix())) {
            throw new WrongAuthenticationTokenException("Wrong authentication token");
        }

        String token = header.replace(jwtConfiguration.getPrefix(), "");
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfiguration.getSecretKey().getBytes())
                .parseClaimsJws(token)
                .getBody();

        if (claims == null){
            throw new WrongAuthenticationTokenException("Wrong authentication token");
        }

        return claims;
    }
}
