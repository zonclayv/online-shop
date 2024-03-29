package by.haidash.shop.core.exception.advice.impl;

import by.haidash.shop.core.exception.InternalServerException;
import by.haidash.shop.core.exception.ResourceAlreadyExistException;
import by.haidash.shop.core.exception.ResourceNotFoundException;
import by.haidash.shop.core.exception.advice.ExceptionAdvice;
import by.haidash.shop.core.exception.data.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public abstract class BaseExceptionAdvice implements ExceptionAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler(ResourceAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse employeeNotFoundHandler(HttpServletRequest request, ResourceAlreadyExistException ex) {

        LOGGER.warn(ex.getCode() + " " + ex.getMessage(), ex);

        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.CONFLICT.value())
                .withError(HttpStatus.CONFLICT.getReasonPhrase())
                .withPath(request.getServletPath())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse employeeNotFoundHandler(HttpServletRequest request, ResourceNotFoundException ex) {

        LOGGER.warn(ex.getCode() + " " + ex.getMessage(), ex);

        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withError(HttpStatus.NOT_FOUND.getReasonPhrase())
                .withPath(request.getServletPath())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse internalServerExceptionHandler(HttpServletRequest request, InternalServerException ex) {

        LOGGER.warn(ex.getCode() + " " + ex.getMessage(), ex);

        return new ErrorResponse.Builder()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withCurrentTimestamp()
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .withError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .withPath(request.getServletPath())
                .build();
    }


}
