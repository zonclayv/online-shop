package by.haidash.shop.security;

import by.haidash.shop.security.data.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
