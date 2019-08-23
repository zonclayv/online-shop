package by.haidash.shop.security.service;

import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.model.UserPrincipal;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    public UserPrincipal getUserPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BaseAuthenticationException("Wrong authentication token.");
        }

        return (UserPrincipal) authentication.getPrincipal();
    }
}
