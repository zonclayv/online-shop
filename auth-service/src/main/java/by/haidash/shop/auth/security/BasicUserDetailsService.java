package by.haidash.shop.auth.security;

import by.haidash.shop.auth.entity.User;
import by.haidash.shop.auth.repository.InternalUserRepository;
import by.haidash.shop.security.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    private final InternalUserRepository userRepository;

    @Autowired
    public BasicUserDetailsService(InternalUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email '" + email + "' not found"));

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPsw())
                .roles("USER")
                .build();
    }
}