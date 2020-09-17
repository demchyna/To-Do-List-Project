package com.softserve.itacademy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullEntityReferenceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView nullEntityReferenceExceptionHandler(NullEntityReferenceException exception) {
        return getModelAndView(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView accessForbiddenExceptionHandler(AccessDeniedException exception) {
        return getModelAndView(HttpStatus.FORBIDDEN, exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView entityNotFoundExceptionHandler(EntityNotFoundException exception) {
        return getModelAndView(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView internalServerErrorHandler(Exception exception) {
        return getModelAndView(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ModelAndView getModelAndView(HttpStatus httpStatus, Exception exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("code", httpStatus.value() + " / " + httpStatus.getReasonPhrase());
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

}
