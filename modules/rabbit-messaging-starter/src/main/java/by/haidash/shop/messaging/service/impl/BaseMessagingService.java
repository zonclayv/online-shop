package by.haidash.shop.messaging.service.impl;

import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.properties.MessagingProperties;
import by.haidash.shop.messaging.service.MessagingService;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.core.ParameterizedTypeReference.forType;

@Service
public class BaseMessagingService implements MessagingService {

    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties messagingProperties;

//    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    public BaseMessagingService(RabbitTemplate rabbitTemplate, MessagingProperties messagingProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingProperties = messagingProperties;
    }


    public MessagingPropertiesEntry getProperties(String key) {
//        rabbitAdmin.initialize();
        MessagingPropertiesEntry messagingPropertiesEntry = messagingProperties.getProperties().get(key);
        if (messagingPropertiesEntry == null) {
            throw new RuntimeException("Messaging properties are missed");
            //TODO customize this exception
        }

        return messagingPropertiesEntry;
    }

    @Override
    public void send(Object request, String exchange, String route) {

        //TODO need to add logger
        rabbitTemplate.convertAndSend(exchange, route, request);
    }

    @Override
    public <P> Optional<P> sendWithResponse(Object request,
                                               Class<P> responseType,
                                               String exchange,
                                               String route) {

        //TODO need to add logger

        return ofNullable(rabbitTemplate.convertSendAndReceiveAsType(exchange, route, request, forType(responseType)));
    }
}
