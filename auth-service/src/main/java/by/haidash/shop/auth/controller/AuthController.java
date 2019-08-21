package by.haidash.shop.auth.controller;

import by.haidash.shop.auth.controller.details.TokenDetails;
import by.haidash.shop.auth.controller.messaging.UserCheckMessage;
import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.service.MessagingService;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.*;

@RestController
public class AuthController {

    @Value("${shop.messaging.keys.user-check}")
    private String userCheckMessagingKey;

    private final Key signKey;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final MessagingService messagingService;

    @Autowired
    public AuthController(MessagingService messagingService,
                          PasswordEncoder passwordEncoder,
                          JwtProperties jwtProperties,
                          JwtTokenService jwtTokenService) {
        this.messagingService = messagingService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;

        this.signKey = jwtTokenService.getPrivateKey(jwtProperties.getPrivateKey());
    }

    @PostMapping("/auth")
    public TokenDetails auth(@RequestParam("username") String username, @RequestParam("password") String password) {

        MessagingPropertiesEntry userMessagingProperties = messagingService.getProperties(userCheckMessagingKey);

        UserCheckMessage userObject = messagingService.sendWithResponse(username,
                UserCheckMessage.class,
                userMessagingProperties.getExchange(),
                userMessagingProperties.getRoute())
                    .orElseThrow(() -> new BadCredentialsException("Invalid username/password supplied."));

        if (!passwordEncoder.matches(password, userObject.getPsw())) {
            throw new BadCredentialsException("Invalid username/password supplied.");
        }

        String token = jwtTokenService.createToken(
                userObject.getEmail(),
                userObject.getId(),
                Collections.singletonList("USER"),
                signKey);

        return new TokenDetails(username, token);
    }
}
