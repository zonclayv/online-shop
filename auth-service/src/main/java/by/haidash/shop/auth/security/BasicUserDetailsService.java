package by.haidash.shop.auth.security;

import by.haidash.shop.auth.entity.User;
import by.haidash.shop.auth.repository.InternalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    @Autowired
    private InternalUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email '" + email + "' not found"));

        List<GrantedAuthority> grantedAuthorities = commaSeparatedStringToAuthorityList("ROLE_USER");

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPsw(), grantedAuthorities);
    }
}