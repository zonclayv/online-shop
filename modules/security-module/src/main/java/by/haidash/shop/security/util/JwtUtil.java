package by.haidash.shop.security.util;

import by.haidash.shop.security.data.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    public Optional<Long> parseUserId(HttpServletRequest request) {

        String header = request.getHeader(jwtConfig.getHeader());
        if(header == null || !header.startsWith(jwtConfig.getPrefix())) {
            return Optional.empty();
        }

        String token = header.replace(jwtConfig.getPrefix(), "");

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.ofNullable(claims.get("userId", Long.class));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
