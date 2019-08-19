package by.haidash.shop.auth.controller;

import by.haidash.shop.core.messaging.service.MessageService;
import by.haidash.shop.messaging.user.model.UserResponseMessage;
import by.haidash.shop.messaging.user.model.UserRequestMessage;
import by.haidash.shop.messaging.user.properties.UserMessagingProperties;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.*;

@RestController
public class AuthController {

    private final Key signKey;
    private final JwtTokenService jwtTokenService;
    private final MessageService<UserRequestMessage> messageService;

    @Autowired
    public AuthController(MessageService messageService,
                          JwtProperties jwtProperties,
                          JwtTokenService jwtTokenService) {
        this.messageService = messageService;
        this.jwtTokenService = jwtTokenService;

        this.signKey = jwtTokenService.getPrivateKey(jwtProperties.getPrivateKey());
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestParam("username") String username, @RequestParam("password") String password) {

        UserRequestMessage userRequestMessage = new UserRequestMessage(username);
        UserResponseMessage userObject = messageService.sendWithResponse(userRequestMessage,
                UserResponseMessage.class,
                UserMessagingProperties.EXCHANGE_NAME,
                UserMessagingProperties.ROUTING_NAME)
                    .orElseThrow(() -> new BadCredentialsException("Invalid username/password supplied."));

        if (!Objects.equals(userObject.getPsw(), password)) {
            throw new BadCredentialsException("Invalid username/password supplied.");
        }

        String token = jwtTokenService.createToken(
                userObject.getEmail(),
                userObject.getId(),
                Collections.singletonList("USER"),
                signKey);

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);

        return ResponseEntity.ok(model);
    }
}
