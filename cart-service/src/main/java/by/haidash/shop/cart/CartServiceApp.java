package by.haidash.shop.cart;

import by.haidash.shop.jpa.JpaModule;
import by.haidash.shop.security.SecurityModule;
import by.haidash.shop.swagger.SwaggerModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CartServiceApp {
    public static void main(String[] args) {
        Class[] sources = {
                CartServiceApp.class,
                SecurityModule.class,
                SwaggerModule.class,
                JpaModule.class
        };

        SpringApplication.run(sources, args);
    }
}
