package ru.practicum.shareit.item;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.AccessDeniedException;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.BadRequestException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class UserErrorHandler {

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String validationFailed(final BadRequestException e) {
        log.error("Возникла ошибка валидации пользователя:\n" + e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(FORBIDDEN)
    public String accessDenied(final AccessDeniedException e) {
        log.error("Возникла ошибка доступа к пользователю\n" + e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String notFound(final NotFoundException e) {
        log.error("Возникла ошибка с поиском пользователя:\n" + e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseStatus(CONFLICT)
    public String conflict(final ConflictException e) {
        log.error("Возникла ошибка совпадения данных пользователей:\n" + e.getMessage());
        return e.getMessage();
    }
}
