package by.haidash.shop.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={
        "by.haidash.shop.cart",
        "by.haidash.shop.security"})
public class CartServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApp.class, args);
    }
}
