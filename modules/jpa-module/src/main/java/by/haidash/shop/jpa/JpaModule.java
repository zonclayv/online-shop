package by.haidash.shop.jpa;

import by.haidash.shop.core.CoreModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaModule {
    public static void main(String[] args) {

        Class[] sources = {
                JpaModule.class,
                CoreModule.class
        };

        SpringApplication.run(sources, args);
    }
}
