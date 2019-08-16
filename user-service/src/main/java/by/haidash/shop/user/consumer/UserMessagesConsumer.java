package by.haidash.shop.user.consumer;

import by.haidash.shop.messaging.user.model.UserResponse;
import by.haidash.shop.messaging.user.model.UserRequest;
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
    public UserResponse consume(@Payload UserRequest request) {

        String email = request.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPsw(user.getPsw());
        userResponse.setId(user.getId());

        return userResponse;
    }
}
