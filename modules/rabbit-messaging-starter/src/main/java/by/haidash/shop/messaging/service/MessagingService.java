package by.haidash.shop.messaging.service;

import by.haidash.shop.messaging.properties.MessagingPropertiesEntry;

import java.util.Optional;

public interface MessagingService {

    MessagingPropertiesEntry getProperties(String key);

    <T> void send(Object request,
                  String exchange,
                  String route);

    <P> Optional<P> sendWithResponse(Object request,
                                     Class<P> responseType,
                                     String exchange,
                                     String route);
}
