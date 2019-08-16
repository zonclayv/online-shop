package by.haidash.shop.user.consumer;

import by.haidash.shop.messaging.user.model.UserResponseMessage;
import by.haidash.shop.messaging.user.model.UserRequestMessage;
import by.haidash.shop.user.entity.User;
import by.haidash.shop.user.exception.UserNotFoundException;
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

    @RabbitListener(queues = "${shop.messaging.listener.user.queue}")
    public UserResponseMessage consume(@Payload UserRequestMessage request) {

        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        UserResponseMessage userResponseMessage = new UserResponseMessage();
        userResponseMessage.setEmail(user.getEmail());
        userResponseMessage.setPsw(user.getPsw());
        userResponseMessage.setId(user.getId());

        return userResponseMessage;
    }
}
