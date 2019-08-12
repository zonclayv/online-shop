package by.haidash.shop.auth;

import by.haidash.shop.jpa.JpaModule;
import by.haidash.shop.jwt.JwtModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApp {

    public static void main(String[] args) {
        Class[] sources = {
                AuthServiceApp.class,
                JwtModule.class,
                JpaModule.class
        };

        SpringApplication.run(sources, args);
    }
}
