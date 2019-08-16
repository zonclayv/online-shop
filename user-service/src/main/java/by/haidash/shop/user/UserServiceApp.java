package by.haidash.shop.user;

import by.haidash.shop.jpa.JpaModule;
import by.haidash.shop.messaging.user.UserMessagingModule;
import by.haidash.shop.security.SecurityModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApp {

    public static void main(String[] args) {
        Class[] sources = {
                UserServiceApp.class,
                JpaModule.class,
                SecurityModule.class,
                UserMessagingModule.class
        };

        SpringApplication.run(sources, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
