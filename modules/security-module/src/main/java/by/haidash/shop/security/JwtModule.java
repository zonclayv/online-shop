package by.haidash.shop.security;

import by.haidash.shop.security.model.JwtConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfiguration.class)
public class JwtModule {
    public static void main(String[] args) {
        SpringApplication.run(JwtModule.class, args);
    }
}