package by.haidash.shop.user.controller;

import by.haidash.shop.core.exception.ResourceAlreadyExistException;
import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.service.EntityMapperService;
import by.haidash.shop.user.controller.details.UserDetails;
import by.haidash.shop.user.entity.User;
import by.haidash.shop.user.repository.UserRepository;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapperService entityMapperService;

    @Autowired
    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          EntityMapperService entityMapperService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityMapperService = entityMapperService;
    }

    @GetMapping
    public List<UserDetails> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> entityMapperService.convertToDetails(user, UserDetails.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDetails getUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find user with id " + id));

        return entityMapperService.convertToDetails(user, UserDetails.class);
    }

    @PostMapping
    public UserDetails addUser(@RequestBody UserDetails userDetails) {

        String email = userDetails.getEmail();
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new ResourceAlreadyExistException("There is an user with provided email address:" + email);
        });

        User user = entityMapperService.convertToEntity(userDetails, User.class);

        // TODO add possibility to registration using just one field - email. After registration link for changing password should be sent.
        String psw = user.getPsw();
        if (!StringUtils.isEmpty(psw)) {
            user.setPsw(passwordEncoder.encode(psw));
        }

        User createdUser = userRepository.save(user);

        return entityMapperService.convertToDetails(createdUser, UserDetails.class);
    }
}
