package by.haidash.shop.user.controller;

import by.haidash.shop.core.exception.ResourceAlreadyExistException;
import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.user.data.NewUser;
import by.haidash.shop.user.entity.User;
import by.haidash.shop.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with id "+ id));
    }

    @PostMapping
    public User addUser(@RequestBody NewUser newUser){

        userRepository.findByEmail(newUser.getEmail()).ifPresent(user -> {
            throw new ResourceAlreadyExistException("There is an user with provided email address:" + newUser.getEmail());
        });

        // TODO add possibility to registration using just one field - email. After registration link for changing password should be sent.
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPsw(passwordEncoder.encode(newUser.getPsw()));
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());

        return userRepository.save(user);
    }
}
