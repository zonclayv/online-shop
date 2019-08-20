package by.haidash.shop.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
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
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Primary
@Configuration
@EnableSwagger2
public class SwaggerConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    @Autowired
    public SwaggerConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        routeLocator.getRoutes()
                .stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Route::getLocation))), ArrayList::new))
                .forEach(route -> resources.add(swaggerResource(route.getLocation(),
                        route.getFullPath().replace("**", "v2/api-docs"),
                        "2.0")));

        return resources;
    }

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
                "Online shop",
                "This is a simple example of using Spring Cloud to build microservices.",
                "0.0.1",
                "https://github.com/zonclayv/online-shop",
                new Contact("Aleh Haidash", "https://github.com/zonclayv/", "aleh.haidash@gmail.com"),
                "GNU General Public License v3.0",
                "https://github.com/zonclayv/online-shop/blob/master/LICENSE",
                Collections.emptyList());
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
