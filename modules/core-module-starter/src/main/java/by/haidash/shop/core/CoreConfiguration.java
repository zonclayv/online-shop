package by.haidash.shop.core;

import by.haidash.shop.core.exception.advice.ExceptionAdvice;
import by.haidash.shop.core.exception.advice.impl.BaseExceptionAdvice;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ExceptionAdvice baseExceptionAdvice() {
        return new BaseExceptionAdvice(){};
    }
}
