package ru.practicum.shareit.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(UserDto userDto) {
        validateEmail(userDto.getEmail());
        User user = buildUser(userDto, getId());
        return userStorage.addUser(user);
    }

    public User update(UserDto userDto, int id) {
        if (userStorage.getUserById(id).isEmpty()) {
            throw new NotFoundException("Пользователя с id " + id + " не существует");
        }
        User user = userStorage.getUserById(id).get();
        if (userDto.getEmail() != null) {
            validateEmail(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        return userStorage.addUser(user);
    }

    public void delete(int id) {
        if (!isExist(id)) {
            throw new NotFoundException("Пользователя с id " + id + " не существует");
        }
        userStorage.deleteById(id);
    }

    public User getById(int id) {
        if (!isExist(id)) {
            throw new NotFoundException("Не найден пользователь с id: " + id);
        }
        return userStorage.getUserById(id).get();
    }

    public Collection<User> getAll() {
        return userStorage.getUsers().values();
    }

    public boolean isExist(int id) {
        return userStorage.getUsers().containsKey(id);
    }

    private User buildUser(UserDto userDto, int id) {
        return User.builder()
                .id(id)
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    private void validateEmail(String email) {
        boolean isUnique = userStorage.getUsers().values().stream()
                .noneMatch(e -> e.getEmail().equals(email));
        if (!isUnique) {
            throw new ConflictException("Пользователь с таким e-mail уже существует.");
        }
    }

    private int getId() {
        return userStorage.getId();
    }
}
