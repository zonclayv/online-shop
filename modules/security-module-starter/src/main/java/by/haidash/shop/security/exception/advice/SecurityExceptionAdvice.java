package by.haidash.shop.security.exception.advice;

import by.haidash.shop.core.exception.data.ErrorResponse;
import by.haidash.shop.security.exception.BaseAuthenticationException;
import by.haidash.shop.security.exception.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class SecurityExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse employeeNotFoundHandler(PermissionDeniedException ex) {
        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.FORBIDDEN.value())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(BaseAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse authenticationErrorHandler(BaseAuthenticationException ex) {
        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.UNAUTHORIZED.value())
                .build();
    }
}
