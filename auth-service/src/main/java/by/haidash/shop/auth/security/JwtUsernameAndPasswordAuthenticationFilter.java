package by.haidash.shop.auth.security;

import by.haidash.shop.security.model.JwtConfiguration;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.security.service.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Set;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final JwtConfiguration jwtConfiguration;
    private final JwtTokenService jwtTokenService;
    private final Key signKey;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager,
                                                      JwtConfiguration jwtConfiguration,
                                                      JwtTokenService jwtTokenService) {
        this.authManager = authManager;
        this.jwtConfiguration = jwtConfiguration;
        this.jwtTokenService = jwtTokenService;

        this.signKey = jwtTokenService.getPrivateKey(jwtConfiguration.getPrivateKey());

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfiguration.getLoginUri(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    Collections.emptyList());

            return authManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        Set<String> authorities = AuthorityUtils.authorityListToSet(auth.getAuthorities());

        String token = jwtTokenService.createToken(
                userPrincipal.getUsername(),
                userPrincipal.getId(),
                authorities,
                signKey);

        response.addHeader(jwtConfiguration.getHeader(), token);
    }

    private final static class UserCredentials {
        private String username, password;

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
    }
}