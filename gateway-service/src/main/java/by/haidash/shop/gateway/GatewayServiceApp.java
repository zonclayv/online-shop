package by.haidash.shop.gateway;

import by.haidash.shop.security.SecurityModule;
import by.haidash.shop.swagger.SwaggerModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApp {
    public static void main(String[] args) {
        Class[] sources = {
                GatewayServiceApp.class,
                SecurityModule.class,
                SwaggerModule.class
        };

        SpringApplication.run(sources, args);
    }
}
