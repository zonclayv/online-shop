package by.haidash.shop.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Primary
@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Online shop application",
                "It's a simple example of using Spring Cloud.",
                "1.0.0",
                "https://github.com/zonclayv/online-shop",
                new Contact("Aleh Haidash", "https://github.com/zonclayv/", "aleh.haidash@gmail.com"),
                "GNU General Public License v3.0",
                "https://github.com/zonclayv/online-shop/blob/master/LICENSE",
                Collections.emptyList());
    }
}
