package by.haidash.shop.jwt;

import by.haidash.shop.jwt.configuration.JwtConfiguration;
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