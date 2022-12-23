package ru.practicum.shareit.item;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.BadRequestException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class ItemErrorHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String failValidation(final BadRequestException e) {
        log.error("Произошла ошибка валидации вещи:\n" + e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public String accessDenied(final AccessDeniedException e) {
        log.error("Произошла ошибка доступа к вещи:\n" + e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String notFound(final NotFoundException e) {
        log.error("Вещь не найдена:\n" + e.getMessage());
        return e.getMessage();
    }
}
