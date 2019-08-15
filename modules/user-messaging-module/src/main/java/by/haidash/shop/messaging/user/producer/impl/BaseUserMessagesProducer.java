package by.haidash.shop.messaging.user.producer.impl;

import by.haidash.shop.messaging.user.model.UserRequest;
import by.haidash.shop.messaging.user.producer.UserMessagesProducer;
import by.haidash.shop.messaging.user.properties.UserMessagingProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.core.ParameterizedTypeReference.forType;

@Component
public class BaseUserMessagesProducer implements UserMessagesProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BaseUserMessagesProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(UserRequest request) {

        //TODO need to add logger
        rabbitTemplate.convertAndSend(UserMessagingProperties.EXCHANGE_NAME,
                UserMessagingProperties.ROUTING_NAME,
                request);
    }

    @Override
    public <T> Optional<T> produceWithResponse(UserRequest request, Class<T> responseType) {

        //TODO need to add logger

        return Optional.ofNullable(rabbitTemplate.convertSendAndReceiveAsType(UserMessagingProperties.EXCHANGE_NAME,
                UserMessagingProperties.ROUTING_NAME,
                request,
                forType(responseType)));
    }
}
