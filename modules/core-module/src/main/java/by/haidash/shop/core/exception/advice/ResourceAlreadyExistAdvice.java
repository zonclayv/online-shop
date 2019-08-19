package by.haidash.shop.core.exception.advice;

import by.haidash.shop.core.exception.ResourceAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ResourceAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String employeeNotFoundHandler(ResourceAlreadyExistException ex) {
        return ex.getMessage();
    }
}
