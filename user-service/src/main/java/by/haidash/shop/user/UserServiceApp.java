package by.haidash.shop.user;

import by.haidash.shop.security.SecurityModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserServiceApp {

    public static void main(String[] args) {
        Class[] sources = {
                UserServiceApp.class,
                SecurityModule.class
        };

        SpringApplication.run(sources, args);
    }
}
