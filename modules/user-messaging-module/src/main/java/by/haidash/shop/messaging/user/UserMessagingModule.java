package by.haidash.shop.messaging.user;

import by.haidash.shop.messaging.user.properties.UserMessagingProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class UserMessagingModule {
    public static void main(String[] args) {
        SpringApplication.run(UserMessagingModule.class, args);
    }
}