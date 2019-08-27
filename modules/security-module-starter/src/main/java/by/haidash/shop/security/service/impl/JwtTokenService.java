package by.haidash.shop.security.service.impl;

import by.haidash.shop.core.exception.InternalServerException;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.security.service.KeyManagerService;
import by.haidash.shop.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtTokenService implements TokenService {

    private final JwtProperties jwtProperties;
    private final KeyManagerService keyManagerService;

    @Autowired
    public JwtTokenService(JwtProperties jwtProperties, KeyManagerService keyManagerService) {
        this.jwtProperties = jwtProperties;
        this.keyManagerService = keyManagerService;
    }

    @Override
    public String createToken(String username, Long userId, List<String> authorities) {

        Key privateKey = keyManagerService.getPrivateKey();
        if (Objects.isNull(privateKey)) {
            throw new InternalServerException("The private key is not present. Please check service properties.");
        }

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .claim("userId", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.RS512, privateKey)
                .compact();

        return jwtProperties.getPrefix() + token;
    }

    @Override
    public UserPrincipal resolveToken(HttpServletRequest request) {

        String header = request.getHeader(jwtProperties.getHeader());
        if (header == null || !header.startsWith(jwtProperties.getPrefix())) {
            throw new BaseAuthenticationException("Wrong authentication token.");
        }

        String token = header.replace(jwtProperties.getPrefix(), "");
        Claims claims = Jwts.parser()
                .setSigningKey(keyManagerService.getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        if (claims == null){
            throw new BaseAuthenticationException("Wrong authentication token.");
        }

        @SuppressWarnings("unchecked")
        List<String> authorities = claims.get("authorities", List.class);
        List<SimpleGrantedAuthority> grantedAuthorities = authorities
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return UserPrincipal.builder()
                .username(claims.getSubject())
                .authorities(grantedAuthorities)
                .id(claims.get("userId", Long.class))
                .build();
    }
}
