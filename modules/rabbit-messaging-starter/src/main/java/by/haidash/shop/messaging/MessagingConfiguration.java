package by.haidash.shop.messaging;

import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.properties.MessagingProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.*;

@EnableRabbit
@Configuration
@ComponentScan
@EnableConfigurationProperties(MessagingProperties.class)
public class MessagingConfiguration {

    @Autowired
    private MessagingProperties messagingProperties;

    @Bean
    public List<Queue> queues() {

        List<Queue> queues = new ArrayList<>();
        Map<String, MessagingPropertiesEntry> properties = messagingProperties.getProperties();
        if (CollectionUtils.isEmpty(properties)) {
            return queues;
        }

        Collection<MessagingPropertiesEntry> messagingEntries = properties.values();
        messagingEntries.stream()
                .map(MessagingPropertiesEntry::getQueue)
                .distinct()
                .forEach(queue -> queues.add(new Queue(queue, true)));

        return queues;
    }

    @Bean
    public List<DirectExchange> exchanges() {

        List<DirectExchange> exchanges = new ArrayList<>();
        Map<String, MessagingPropertiesEntry> properties = messagingProperties.getProperties();
        if (CollectionUtils.isEmpty(properties)) {
            return exchanges;
        }

        Collection<MessagingPropertiesEntry> messagingEntries = properties.values();
        messagingEntries.stream()
                .map(MessagingPropertiesEntry::getExchange)
                .distinct()
                .forEach(exchange -> exchanges.add(new DirectExchange(exchange)));

        return exchanges;
    }

    @Bean
    public List<Binding> bindings() {

        List<Binding> bindings = new ArrayList<>();
        Map<String, MessagingPropertiesEntry> properties = messagingProperties.getProperties();
        if (CollectionUtils.isEmpty(properties)) {
            return bindings;
        }

        Collection<MessagingPropertiesEntry> messagingEntries = properties.values();
        for (MessagingPropertiesEntry entry : messagingEntries) {
            bindings.add(new Binding(entry.getQueue(),
                    Binding.DestinationType.QUEUE,
                    entry.getExchange(),
                    entry.getRoute(),
                    null));
        }

        return bindings;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
