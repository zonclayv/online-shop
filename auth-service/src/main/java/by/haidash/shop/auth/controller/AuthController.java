package by.haidash.shop.auth.controller;

import by.haidash.shop.auth.controller.messaging.UserCheckMessage;
import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.service.MessagingService;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static java.lang.String.format;

@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Value("${shop.messaging.keys.user-check}")
    private String userCheckMessagingKey;

    private final JwtProperties jwtProperties;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MessagingService messagingService;

    @Autowired
    public AuthController(MessagingService messagingService,
                          PasswordEncoder passwordEncoder,
                          JwtProperties jwtProperties,
                          TokenService tokenService) {

        this.messagingService = messagingService;
        this.passwordEncoder = passwordEncoder;
        this.jwtProperties = jwtProperties;
        this.tokenService = tokenService;
    }

    @PostMapping("/auth")
    public void auth(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     HttpServletResponse res) {

        LOGGER.info(String.format("A new attempt to obtain a token for a user with username '%s'.", username));

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

        String token = tokenService.createToken(
                response.getEmail(),
                response.getId(),
                Collections.singletonList("USER"));

        res.addHeader(jwtProperties.getHeader(), token);

        LOGGER.info(format("Authorization token was successfully generated for user with username '%s'.", username));
    }
}
