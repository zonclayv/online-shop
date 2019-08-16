package by.haidash.shop.messaging.user;

import by.haidash.shop.core.CoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserMessagingModule {
    public static void main(String[] args) {

        Class[] sources = {
                UserMessagingModule.class,
                CoreModule.class
        };

        SpringApplication.run(sources, args);
    }
}