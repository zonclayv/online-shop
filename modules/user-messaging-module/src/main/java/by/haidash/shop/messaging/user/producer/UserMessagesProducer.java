package by.haidash.shop.messaging.user.producer;

import by.haidash.shop.messaging.user.model.UserRequest;

import java.util.Optional;

public interface UserMessagesProducer {

    void produce(UserRequest request);

    <T> Optional<T> produceWithResponse(UserRequest request, Class<T> responseType);
}
