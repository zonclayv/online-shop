package by.haidash.shop.security.filter;

import by.haidash.shop.security.model.UserPrincipal;
import by.haidash.shop.security.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    private final TokenService tokenService;

    public JwtTokenAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        LOGGER.info("New authorization attempt.");

        try {

            UserPrincipal userPrincipal = tokenService.resolveToken(request);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userPrincipal,
                        null,
                    userPrincipal.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {

            LOGGER.warn("An authorization attempt failed.", e);

            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

}
