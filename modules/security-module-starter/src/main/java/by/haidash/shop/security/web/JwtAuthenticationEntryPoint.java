package by.haidash.shop.security.web;

import by.haidash.shop.core.exception.data.ErrorResponse;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.haidash.shop.security.exception.BaseAuthenticationException.UNAUTHORIZED_EXCEPTION_CODE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    private final ObjectMapper mapper;

    @Autowired
    public JwtAuthenticationEntryPoint(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        LOGGER.warn(UNAUTHORIZED_EXCEPTION_CODE + " " + e.getMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .withCode(UNAUTHORIZED_EXCEPTION_CODE)
                .withMessage(e.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.UNAUTHORIZED.value())
                .withError(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .withPath(httpServletRequest.getServletPath())
                .build();

        String json = mapper.writeValueAsString(errorResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
