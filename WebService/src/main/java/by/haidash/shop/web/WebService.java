package by.haidash.shop.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters=false)
public class WebService {

    public static void main(String[] args) {
        SpringApplication.run(WebService.class, args);
    }
}
