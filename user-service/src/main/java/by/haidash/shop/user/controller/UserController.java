package by.haidash.shop.user.controller;

import by.haidash.shop.user.data.NewUser;
import by.haidash.shop.user.entity.User;
import by.haidash.shop.user.exception.UserNotFoundException;
import by.haidash.shop.user.exception.EmailExistException;
import by.haidash.shop.user.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(description = "Set of endpoints for creating, retrieving, updating and deleting of user.")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    @ApiOperation("Returns list of all users.")
    public List<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns a specific product by their identifier. 404 if does not exist.")
    public User getUser(@ApiParam("Id of the user to be obtained. Cannot be empty.")
                            @PathVariable Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PostMapping("/")
    @ApiOperation("Registration of new user.")
    public User register(@ApiParam("User information for a new user to be created.")
                             @RequestBody NewUser newUser){

        // TODO need implement checking
        if (false) {
            throw new EmailExistException("There is an user with that email address:" + newUser.getEmail());
        }

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
