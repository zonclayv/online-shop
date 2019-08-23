package by.haidash.shop.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.security.Principal;
import java.util.*;

public class UserPrincipal implements Principal {

    private Long id;
    private String password;
    private final String username;
    private final Set<GrantedAuthority> authorities;

    public UserPrincipal(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = new HashSet<>(authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @Override
    public String getName() {
        return username;
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;

        private UserBuilder() {
        }

        public UserPrincipal.UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserPrincipal.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public UserPrincipal.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public UserPrincipal.UserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList<>(
                    roles.length);
            for (String role : roles) {
                Assert.isTrue(!role.startsWith("ROLE_"), () -> role
                        + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            return authorities(authorities);
        }

        public UserPrincipal.UserBuilder authorities(GrantedAuthority... authorities) {
            return authorities(Arrays.asList(authorities));
        }

        public UserPrincipal.UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        public UserPrincipal.UserBuilder authorities(String... authorities) {
            return authorities(AuthorityUtils.createAuthorityList(authorities));
        }

        public UserPrincipal build() {
            return new UserPrincipal(id,username, password, authorities);
        }
    }
}
