package by.haidash.shop.jpa;

import by.haidash.shop.core.service.EntityMapperService;
import by.haidash.shop.jpa.initializer.JpaPropertiesInitializer;
import by.haidash.shop.jpa.service.JpaEntityMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public EntityMapperService entityMapperService(ModelMapper modelMapper){
        return new JpaEntityMapperService(modelMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public JpaPropertiesInitializer jpaPropertiesInitializer(){
        return new JpaPropertiesInitializer();
    }
}
