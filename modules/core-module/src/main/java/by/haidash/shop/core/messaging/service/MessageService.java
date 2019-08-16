package by.haidash.shop.core.messaging.service;

import by.haidash.shop.core.messaging.model.BaseMessage;

import java.util.Optional;

public interface MessageService<T extends BaseMessage> {

    void send(T request, String exchange, String route);

    <P> Optional<P> sendWithResponse(T request, Class<P> responseType, String exchange, String route);
}
