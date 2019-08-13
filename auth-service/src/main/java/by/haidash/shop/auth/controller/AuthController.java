package by.haidash.shop.auth.controller;

import by.haidash.shop.auth.entity.User;
import by.haidash.shop.auth.repository.InternalUserRepository;
import by.haidash.shop.security.model.JwtConfiguration;
import by.haidash.shop.security.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class AuthController {

    private final Key signKey;
    private final JwtTokenService jwtTokenService;
    private final InternalUserRepository userRepository;

    @Autowired
    public AuthController(InternalUserRepository userRepository,
                          JwtConfiguration jwtConfiguration,
                          JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.jwtTokenService = jwtTokenService;

        this.signKey = jwtTokenService.getPrivateKey(jwtConfiguration.getPrivateKey());
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestParam("username") String username, @RequestParam("password") String password) {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username/password supplied."));

        if (!Objects.equals(user.getPsw(), password)) {
            throw new BadCredentialsException("Invalid username/password supplied.");
        }

        String token = jwtTokenService.createToken(
                user.getEmail(),
                user.getId(),
                Collections.singletonList("USER"),
                signKey);

        Map<Object, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);

        return ResponseEntity.ok(model);
    }
}
