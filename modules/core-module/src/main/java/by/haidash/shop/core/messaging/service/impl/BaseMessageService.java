package by.haidash.shop.core.messaging.service.impl;

import by.haidash.shop.core.messaging.model.BaseMessage;
import by.haidash.shop.core.messaging.service.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.core.ParameterizedTypeReference.forType;

@Service
public abstract class BaseMessageService<T extends BaseMessage> implements MessageService<T> {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BaseMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(T request, String exchange, String route) {

        //TODO need to add logger
        rabbitTemplate.convertAndSend(exchange, route, request);
    }

    @Override
    public <P> Optional<P> sendWithResponse(T request, Class<P> responseType, String exchange, String route) {

        //TODO need to add logger

        return ofNullable(rabbitTemplate.convertSendAndReceiveAsType(exchange, route, request, forType(responseType)));
    }
}
