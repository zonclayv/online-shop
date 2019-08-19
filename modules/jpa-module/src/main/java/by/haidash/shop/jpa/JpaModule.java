package by.haidash.shop.jpa;

import by.haidash.shop.core.CoreModule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpaModule {
    public static void main(String[] args) {

        Class[] sources = {
                JpaModule.class,
                CoreModule.class
        };

        SpringApplication.run(sources, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
