package by.haidash.shop.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CatalogService {
    public static void main(String[] args) {
        SpringApplication.run(CatalogService.class, args);
    }
}
