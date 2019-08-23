package by.haidash.shop.auth.controller;

import by.haidash.shop.auth.controller.messaging.UserCheckMessage;
import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.service.MessagingService;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;

@RestController
public class AuthController {

    @Value("${shop.messaging.keys.user-check}")
    private String userCheckMessagingKey;

    private final Key signKey;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final MessagingService messagingService;

    @Autowired
    public AuthController(MessagingService messagingService,
                          PasswordEncoder passwordEncoder,
                          JwtProperties jwtProperties,
                          JwtProperties jwtProperties1, JwtTokenService jwtTokenService) {
        this.messagingService = messagingService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProperties = jwtProperties1;
        this.jwtTokenService = jwtTokenService;

        this.signKey = jwtTokenService.getPrivateKey(jwtProperties.getPrivateKey());
    }

    @PostMapping("/auth")
    public void auth(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     HttpServletResponse res) {

        MessagingPropertiesEntry userMessagingProperties = messagingService.getProperties(userCheckMessagingKey);

        UserCheckMessage request = new UserCheckMessage();
        request.setEmail(username);

        UserCheckMessage response = messagingService.sendWithResponse(request,
                UserCheckMessage.class,
                userMessagingProperties.getExchange(),
                userMessagingProperties.getRoute())
                    .orElseThrow(() -> new BaseAuthenticationException("Invalid username/password supplied."));

        if (StringUtils.isEmpty(response.getId()) || !passwordEncoder.matches(password, response.getPsw())) {
            throw new BaseAuthenticationException("Invalid username/password supplied.");
        }

        String token = jwtTokenService.createToken(
                response.getEmail(),
                response.getId(),
                Collections.singletonList("USER"),
                signKey);

        res.addHeader(jwtProperties.getHeader(), token);
    }
}
