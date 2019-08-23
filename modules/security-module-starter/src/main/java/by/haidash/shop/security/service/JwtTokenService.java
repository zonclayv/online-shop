package by.haidash.shop.security.service;

import by.haidash.shop.core.exception.InternalServerException;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {

    private static final String PATTERN_PRIVATE_KEY = "(-+BEGIN PRIVATE KEY-+\\r?\\n|-+END PRIVATE KEY-+\\r?\\n?)";
    private static final String PATTERN_PUBLIC_KEY = "(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)";

    private final JwtProperties jwtProperties;
    private final Key publicKey;

    @Autowired
    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        this.publicKey = getPublicKey(jwtProperties.getPublicKey());
    }

    public String createToken(String username, Long userId, List<String> authorities, Key key) {

        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject(username)
                .claim("authorities", authorities)
                .claim("userId", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtProperties.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.RS512, key)
                .compact();

        return jwtProperties.getPrefix() + token;
    }

    public UserPrincipal resolveToken(HttpServletRequest request) {

        String header = request.getHeader(jwtProperties.getHeader());
        if (header == null || !header.startsWith(jwtProperties.getPrefix())) {
            throw new BaseAuthenticationException("Wrong authentication token.");
        }

        String token = header.replace(jwtProperties.getPrefix(), "");
        Claims claims = Jwts.parser()
                .setSigningKey(publicKey)
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

    public Key getPublicKey(String key) {

        String publicKey = key.replaceAll(PATTERN_PUBLIC_KEY, "");
        byte[] decodedKey = Base64.decodeBase64(publicKey);

        try {

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);

            return KeyFactory.getInstance("RSA")
                    .generatePublic(spec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalServerException("An error occurred while parsing the public key.", e);
        }
    }


    public Key getPrivateKey(String key) {

        String privateKey = key.replaceAll(PATTERN_PRIVATE_KEY, "");
        byte[] decodedKey = Base64.decodeBase64(privateKey);

        try {

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decodedKey);

            return KeyFactory.getInstance("RSA")
                    .generatePrivate(spec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalServerException("An error occurred while parsing the private key.", e);
        }
    }
}
