package by.haidash.shop.messaging;

import by.haidash.shop.messaging.initializer.RabbitPropertiesInitializer;
import by.haidash.shop.messaging.properties.MessagingProperties;
import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.service.MessagingService;
import by.haidash.shop.messaging.service.impl.BaseMessagingService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;

@EnableRabbit
@Configuration
@EnableConfigurationProperties(MessagingProperties.class)
public class MessagingConfiguration {

    private final AmqpAdmin amqpAdmin;
    private final MessagingProperties messagingProperties;

    @Autowired
    public MessagingConfiguration(AmqpAdmin amqpAdmin, MessagingProperties messagingProperties) {
        this.messagingProperties = messagingProperties;
        this.amqpAdmin = amqpAdmin;
    }

    @PostConstruct
    public void createQueues() {

        Map<String, MessagingPropertiesEntry> properties = messagingProperties.getProperties();
        if (CollectionUtils.isEmpty(properties)) {
            return;
        }

        Collection<MessagingPropertiesEntry> messagingEntries = properties.values();

        messagingEntries.stream()
                .map(MessagingPropertiesEntry::getQueue)
                .distinct()
                .forEach(queue -> amqpAdmin.declareQueue(new Queue(queue, true)));

        messagingEntries.stream()
                .map(MessagingPropertiesEntry::getExchange)
                .distinct()
                .forEach(exchange -> amqpAdmin.declareExchange(new DirectExchange(exchange)));

        for (MessagingPropertiesEntry entry : messagingEntries) {
            amqpAdmin.declareBinding(new Binding(entry.getQueue(),
                    Binding.DestinationType.QUEUE,
                    entry.getExchange(),
                    entry.getRoute(),
                    null));
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public RabbitPropertiesInitializer rabbitPropertiesInitializer() {
        return new RabbitPropertiesInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessagingService messagingService(RabbitTemplate rabbitTemplate, MessagingProperties messagingProperties) {
        return new BaseMessagingService(rabbitTemplate, messagingProperties);
    }
}
