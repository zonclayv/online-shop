package by.haidash.shop.security.web;

import by.haidash.shop.core.exception.data.ErrorResponse;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse errorResponse = new ErrorResponse.Builder()
                .withCode(BaseAuthenticationException.UNAUTHORIZED_EXCEPTION_CODE)
                .withMessage(e.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpServletResponse.SC_UNAUTHORIZED)
                .withPath(httpServletRequest.getServletPath())
                .build();

        String json = mapper.writeValueAsString(errorResponse);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
