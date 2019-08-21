package by.haidash.shop.cart;

import by.haidash.shop.security.SecurityModule;
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
        };

        SpringApplication.run(sources, args);
    }
}
