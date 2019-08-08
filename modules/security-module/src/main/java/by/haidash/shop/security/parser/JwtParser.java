package by.haidash.shop.security.parser;

import by.haidash.shop.security.configuration.JwtConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtParser {

    private final JwtConfiguration jwtConfiguration;

    @Autowired
    public JwtParser(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public Optional<Long> parseUserId(HttpServletRequest request) {

        String header = request.getHeader(jwtConfiguration.getHeader());
        if(header == null || !header.startsWith(jwtConfiguration.getPrefix())) {
            return Optional.empty();
        }

        String token = header.replace(jwtConfiguration.getPrefix(), "");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfiguration.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.ofNullable(claims.get("userId", Long.class));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
