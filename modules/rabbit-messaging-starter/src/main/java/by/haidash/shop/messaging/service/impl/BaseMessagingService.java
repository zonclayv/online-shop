package by.haidash.shop.messaging.service.impl;

import by.haidash.shop.core.exception.InternalServerException;
import by.haidash.shop.messaging.properties.MessagingProperties;
import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;
import by.haidash.shop.messaging.service.MessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.springframework.core.ParameterizedTypeReference.forType;

public class BaseMessagingService implements MessagingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessagingService.class);

    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties messagingProperties;

    @Autowired
    public BaseMessagingService(RabbitTemplate rabbitTemplate, MessagingProperties messagingProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingProperties = messagingProperties;
    }

    public MessagingPropertiesEntry getProperties(String key) {
        MessagingPropertiesEntry messagingPropertiesEntry = messagingProperties.getProperties().get(key);
        if (messagingPropertiesEntry == null) {
            throw new InternalServerException("Messaging properties are missed");
        }

        return messagingPropertiesEntry;
    }

    @Override
    public void send(Object request, String exchange, String route) {

        LOGGER.info(format("Message will be sent using exchange '%s' and route '%s'.", exchange, route));

        rabbitTemplate.convertAndSend(exchange, route, request);

        LOGGER.info(format("Message was successfully sent using exchange '%s' and route '%s'.", exchange, route));
    }

    @Override
    public <P> Optional<P> sendWithResponse(Object request,
                                            Class<P> responseType,
                                            String exchange,
                                            String route) {

        LOGGER.info(format("Message with response type '%s' will be sent using exchange '%s' and route '%s'.",
                responseType.getSimpleName(),
                exchange,
                route));

        return ofNullable(rabbitTemplate.convertSendAndReceiveAsType(exchange, route, request, forType(responseType)));
    }
}
