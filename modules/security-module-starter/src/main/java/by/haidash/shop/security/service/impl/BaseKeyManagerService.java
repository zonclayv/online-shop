package by.haidash.shop.security.service.impl;

import by.haidash.shop.core.exception.InternalServerException;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.service.KeyManagerService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
public class BaseKeyManagerService implements KeyManagerService {

    private static final String PATTERN_PRIVATE_KEY = "(-+BEGIN PRIVATE KEY-+\\r?\\n|-+END PRIVATE KEY-+\\r?\\n?)";
    private static final String PATTERN_PUBLIC_KEY = "(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)";

    private final Key publicKey;
    private final Key privateKey;

    public BaseKeyManagerService(JwtProperties jwtProperties) {
        this.publicKey = resolvePublicKey(jwtProperties.getPublicKey());
        this.privateKey = (!StringUtils.isEmpty(jwtProperties.getPrivateKey()))
                ? resolvePrivateKey(jwtProperties.getPrivateKey())
                : null;
    }

    @Override
    public Key getPublicKey() {
        return publicKey;
    }

    @Override
    public Key getPrivateKey() {
        return privateKey;
    }

    private Key resolvePrivateKey(String key) {

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

    private Key resolvePublicKey(String key) {

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
}
