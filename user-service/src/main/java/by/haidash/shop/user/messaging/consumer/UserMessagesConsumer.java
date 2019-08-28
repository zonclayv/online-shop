package by.haidash.shop.user.messaging.consumer;

import by.haidash.shop.user.messaging.data.UserCheckMessage;
import by.haidash.shop.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserMessagesConsumer {

    private final UserRepository userRepository;

    @Autowired
    public UserMessagesConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "${shop.messaging.listener.user-check}")
    public UserCheckMessage consume(@Payload UserCheckMessage request) {

        UserCheckMessage message = new UserCheckMessage();
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    message.setEmail(user.getEmail());
                    message.setPsw(user.getPsw());
                    message.setId(user.getId());
                });

        return message;
    }
}
