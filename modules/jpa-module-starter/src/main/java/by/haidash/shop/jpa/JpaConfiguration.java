package by.haidash.shop.jpa;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class JpaConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
