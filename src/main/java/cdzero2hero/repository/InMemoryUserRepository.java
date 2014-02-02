package cdzero2hero.repository;

import cdzero2hero.domain.User;

import java.util.ArrayList;
import java.util.List;

public enum InMemoryUserRepository implements UserRepository {
    INSTANCE;

    private int nextId;

    private final List<User> users = new ArrayList<>();

    @Override
    public User getUserById(Integer id) {
        for (User user : users) {
            if (id.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User getUserByName(String name) {
        for (User user : users) {
            if (name.equals(user.getName())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {
        if (user.getId() == null) {
            user.setId(++nextId);
        }
        users.add(user);
    }
}
