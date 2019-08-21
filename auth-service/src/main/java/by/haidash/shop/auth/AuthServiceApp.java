package by.haidash.shop.auth;

import by.haidash.shop.core.CoreModule;
import by.haidash.shop.security.SecurityModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApp {

    public static void main(String[] args) {
        Class[] sources = {
                AuthServiceApp.class,
                SecurityModule.class,
                CoreModule.class
        };

        SpringApplication.run(sources, args);
    }
}
