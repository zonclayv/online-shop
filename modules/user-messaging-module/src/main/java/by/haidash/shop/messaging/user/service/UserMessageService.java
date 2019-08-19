package by.haidash.shop.messaging.user.service;

import by.haidash.shop.core.messaging.service.impl.BaseMessageService;
import by.haidash.shop.messaging.user.model.UserRequestMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageService extends BaseMessageService<UserRequestMessage> {

    @Autowired
    public UserMessageService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }
}
