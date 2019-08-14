package by.haidash.shop.security;

import by.haidash.shop.security.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityModule {
    public static void main(String[] args) {
        SpringApplication.run(SecurityModule.class, args);
    }
}