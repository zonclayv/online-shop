package by.haidash.shop.security.service.impl;

import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.security.service.ContextService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService implements ContextService {

    @Override
    public UserPrincipal getUserPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BaseAuthenticationException("Wrong authentication token.");
        }

        return (UserPrincipal) authentication.getPrincipal();
    }
}
