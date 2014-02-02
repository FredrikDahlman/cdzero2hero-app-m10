package cdzero2hero.repository;

import cdzero2hero.domain.User;

public interface UserRepository {
    User getUserById(Integer id);

    User getUserByName(String name);

    void addUser(User user);
}
