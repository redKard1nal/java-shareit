package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@RequestBody @Valid UserDto userDto) {
        validate(userDto);
        return userService.add(userDto);
    }

    @PatchMapping("/{userId}")
    public User update(@RequestBody @Valid UserDto userDto,
                       @PathVariable int userId) {
        return userService.update(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable int userId) {
        userService.delete(userId);
    }

    @GetMapping("/{userId}")
    public User get(@PathVariable int userId) {
        return userService.getById(userId);
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }

    private void validate(UserDto userDto) throws ValidationException {
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new ValidationException("Не указано имя пользователя.");
        }
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new ValidationException("Не указан e-mail пользователя.");
        }
    }
}
