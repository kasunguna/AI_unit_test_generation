package org.example;

import java.util.Collections;
import java.util.List;

//This class provides dummy implementation for my AI investigation.
public class UserRepository {
    public User save(User user) {
        return user;
    }

    public User findByUsername(String username) {
        return new User();
    }

    public List<User> findAll() {
        return Collections.emptyList();
    }

    public boolean delete(User user) {
        return true;
    }
}
