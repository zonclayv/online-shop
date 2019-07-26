package by.haidash.microservice.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BasicUserDetailsService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    private List<InternalUser> users = Arrays.asList(
            new InternalUser(1, "user", encoder.encode("12345"), "USER"),
            new InternalUser(2, "admin", encoder.encode("12345"), "ADMIN")
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        for(InternalUser internalUser : users) {
            if(internalUser.getUsername().equals(username)) {
                List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                        .commaSeparatedStringToAuthorityList("ROLE_" + internalUser.getRole());

                return new User(internalUser.getUsername(), internalUser.getPassword(), grantedAuthorities);
            }
        }

        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

    private static class InternalUser {
        private Integer id;
        private String username, password;
        private String role;

        public InternalUser(Integer id, String username, String password, String role) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}