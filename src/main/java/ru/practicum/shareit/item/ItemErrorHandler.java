package ru.practicum.shareit.item;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ItemErrorHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String ValidationFailed(final ValidationException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public String AccessDenied(final AccessDeniedException e) {
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String NotFound(final NotFoundException e) {
        return e.getMessage();
    }
}
