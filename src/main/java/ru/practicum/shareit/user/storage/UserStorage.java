package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int availableId = 0;

    public User addUser(User user) {
        users.remove(user.getId());
        users.put(user.getId(), user);
        return user;
    }

    public Optional<User> getUserById(int id) {
        return Optional.of(users.get(id));
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public void deleteById(int id) {
        users.remove(id);
    }

    public int getId() {
        return ++availableId;
    }
}
