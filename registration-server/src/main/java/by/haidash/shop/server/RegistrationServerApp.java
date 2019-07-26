package by.haidash.shop.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegistrationServerApp {

    public static void main(String[] args) {
        SpringApplication.run(RegistrationServerApp.class, args);
    }
}
