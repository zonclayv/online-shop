package by.haidash.shop.jwt.advice;

import by.haidash.shop.jwt.exception.WrongAuthenticationTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class WrongAuthenticationTokenAdvice {

    @ResponseBody
    @ExceptionHandler(WrongAuthenticationTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String employeeNotFoundHandler(WrongAuthenticationTokenException ex) {
        return ex.getMessage();
    }
}
