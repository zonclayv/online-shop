package by.haidash.shop.core.exception.advice;

import by.haidash.shop.core.exception.InternalServerException;
import by.haidash.shop.core.exception.ResourceAlreadyExistException;
import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.exception.data.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
class BaseExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse employeeNotFoundHandler(ResourceAlreadyExistException ex) {
        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.CONFLICT.value())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse employeeNotFoundHandler(ResourceNotFoundException ex) {
        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerExceptionHandler(InternalServerException ex) {
        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
    }
}
