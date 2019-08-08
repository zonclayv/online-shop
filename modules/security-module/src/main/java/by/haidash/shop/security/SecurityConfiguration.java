package by.haidash.shop.security;

import by.haidash.shop.security.configuration.JwtConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtConfiguration.class)
public class SecurityConfiguration {

}
