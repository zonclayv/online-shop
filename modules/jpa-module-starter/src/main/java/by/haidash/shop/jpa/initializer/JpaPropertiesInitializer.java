package by.haidash.shop.jpa.initializer;

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
public class JpaPropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String JPA_PROPERTIES_NAME = "jpa-properties";
    public static final String JPA_PROPERTIES_YML_PATH = "classpath:jpa-properties.yml";

    private ResourceLoader loader = new DefaultResourceLoader();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        try {
            Resource resource = loader.getResource(JPA_PROPERTIES_YML_PATH);
            List<PropertySource<?>> propertySources = new YamlPropertySourceLoader().load(JPA_PROPERTIES_NAME, resource);
            for (PropertySource<?> source : propertySources) {
                applicationContext.getEnvironment().getPropertySources().addLast(source);
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
