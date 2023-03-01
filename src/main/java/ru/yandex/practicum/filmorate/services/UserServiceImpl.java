package ru.yandex.practicum.filmorate.services;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.NotValidUserException;
import ru.yandex.practicum.filmorate.exceptions.UserNotExistException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserServiceImpl implements UserService {
    private final List<User> allUsers = new ArrayList<>();

    @Override
    public User addUser(User user) {
        if (isUserValid(user)) {
            int id = user.getId();
            allUsers.add(user);
            log.info("User {} has been added to list", id);
            return getUserById(id);
        }
        throw new NotValidUserException();
    }

    @Override
    public User editUser(User user) {
        int id = user.getId();
        User oldUser = getUserById(id);
        if (oldUser != null) {
            allUsers.remove(oldUser);
            allUsers.add(user);
            log.info("User {} has been edited", id);
            return getUserById(id);
        } else {
            throw new UserNotExistException();
        }
    }

    @Override
    public List<User> getAll() {
        log.info("All users have been uploaded");
        return allUsers;
    }

    private User getUserById(int id) {
        for (User user : allUsers) {
            if (user.getId() == id) {
                return user;
            }
        }
        log.error("User {} was not found", id);
        return null;
    }

    private boolean isUserValid(User user) {
        if (user.getLogin().contains(" ") || user.getBirthday() > System.currentTimeMillis()) {
            log.error("Fields of user {} seem to be invalid", user.getLogin());
            return false;
        }
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.info("User's name was changed to {}", user.getName());
        }
        return true;
    }
}
