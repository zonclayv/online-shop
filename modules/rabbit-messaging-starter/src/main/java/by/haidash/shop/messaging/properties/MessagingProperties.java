package by.haidash.shop.messaging.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("shop.messaging")
public class MessagingProperties {

    private Map<String, MessagingPropertiesEntry> properties;

    public Map<String, MessagingPropertiesEntry> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, MessagingPropertiesEntry> properties) {
        this.properties = properties;
    }
}
