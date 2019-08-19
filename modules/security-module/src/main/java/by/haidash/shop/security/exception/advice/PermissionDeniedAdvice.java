package by.haidash.shop.security.exception.advice;

import by.haidash.shop.security.exception.PermissionDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class PermissionDeniedAdvice {

    @ResponseBody
    @ExceptionHandler(PermissionDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String employeeNotFoundHandler(PermissionDeniedException ex) {
        return ex.getMessage();
    }
}
