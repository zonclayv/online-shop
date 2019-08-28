package by.haidash.shop.security.initializer;

import by.haidash.shop.core.exception.InternalServerException;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String PROPERTIES_NAME = "properties-security";
    private static final String PROPERTIES_YML_PATH = "classpath:application-security.yml";

    private final ResourceLoader loader = new DefaultResourceLoader();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            Resource resource = loader.getResource(PROPERTIES_YML_PATH);
            List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load(PROPERTIES_NAME, resource);
            for (PropertySource<?> source : propertySources) {
                applicationContext.getEnvironment().getPropertySources().addLast(source);
            }
        } catch (IOException ex) {
            throw new InternalServerException("An error occurred while reading properties from " + PROPERTIES_YML_PATH, ex);
        }
    }

}
