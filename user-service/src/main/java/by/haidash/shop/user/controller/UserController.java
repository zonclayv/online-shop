package by.haidash.shop.user.controller;

import by.haidash.shop.user.data.UserDto;
import by.haidash.shop.user.entity.User;
import by.haidash.shop.user.exception.UserNotFoundException;
import by.haidash.shop.user.exception.EmailExistException;
import by.haidash.shop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/accounts")
    public List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/accounts/{id}")
    public User getById(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/accounts/signin")
    public User register(UserDto dto){

        // TODO need implement checking
        if (false) {
            throw new EmailExistException("There is an user with that email address:" + dto.getEmail());
        }

        // TODO add possibility to registration using just one field - email. After registration link for changing password should be sent.
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPsw(passwordEncoder.encode(dto.getPsw()));
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        return userRepository.save(user);
    }
}
