package by.haidash.shop.security;

import by.haidash.shop.security.config.DefaultSecurityConfig;
import by.haidash.shop.security.exception.advice.SecurityExceptionAdvice;
import by.haidash.shop.security.initializer.SecurityPropertiesInitializer;
import by.haidash.shop.security.properties.JwtProperties;
import by.haidash.shop.security.service.KeyManagerService;
import by.haidash.shop.security.service.PrincipalService;
import by.haidash.shop.security.service.TokenService;
import by.haidash.shop.security.service.impl.BaseKeyManagerService;
import by.haidash.shop.security.service.impl.BasePrincipalService;
import by.haidash.shop.security.service.impl.JwtTokenService;
import by.haidash.shop.security.web.JwtAuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityExceptionAdvice baseExceptionAdvice() {
        return new SecurityExceptionAdvice() {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultSecurityConfig defaultSecurityConfig(JwtProperties jwtProperties,
                                                       TokenService tokenService,
                                                       JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        return new DefaultSecurityConfig(jwtProperties, tokenService, jwtAuthenticationEntryPoint) {
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint(ObjectMapper mapper) {
        return new JwtAuthenticationEntryPoint(mapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public SecurityPropertiesInitializer securityPropertiesInitializer() {
        return new SecurityPropertiesInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService(JwtProperties jwtProperties, KeyManagerService keyManagerService) {
        return new JwtTokenService(jwtProperties, keyManagerService);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeyManagerService keyManagerService(JwtProperties jwtProperties) {
        return new BaseKeyManagerService(jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public PrincipalService principalService() {
        return new BasePrincipalService();
    }
}