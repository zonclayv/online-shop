package by.haidash.shop.security.exception.advice;

import by.haidash.shop.core.exception.advice.ExceptionAdvice;
import by.haidash.shop.core.exception.data.ErrorResponse;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.exception.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public abstract class SecurityExceptionAdvice implements ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse employeeNotFoundHandler(HttpServletRequest request, PermissionDeniedException ex) {

        LOGGER.warn(ex.getCode() + " " + ex.getMessage(), ex);

        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.FORBIDDEN.value())
                .withError(HttpStatus.FORBIDDEN.getReasonPhrase())
                .withPath(request.getServletPath())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(BaseAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse authenticationErrorHandler(HttpServletRequest request, BaseAuthenticationException ex) {

        LOGGER.warn(ex.getCode() + " " + ex.getMessage(), ex);

        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.UNAUTHORIZED.value())
                .withError(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .withPath(request.getServletPath())
                .build();
    }
}
