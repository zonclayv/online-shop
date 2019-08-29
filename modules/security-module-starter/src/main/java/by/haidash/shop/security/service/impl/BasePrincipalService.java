package by.haidash.shop.security.service.impl;

import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.security.service.PrincipalService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BasePrincipalService implements PrincipalService {

    @Override
    public UserPrincipal getUserPrincipal() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BaseAuthenticationException("Wrong authentication token.");
        }

        return (UserPrincipal) authentication.getPrincipal();
    }
}
