package org.example;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            throw new RuntimeException("Record already exists!");
        }

        return userRepository.save(user);
    }

    public List<User> readAll() {
        List<User> users = new ArrayList<User>();

        List<User> results = userRepository.findAll();
        for (User r : results) {
            users.add(r);
        }

        return users;
    }

    public User update(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            return null;
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    public Boolean delete(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null) {
            return false;
        }

        return userRepository.delete(existingUser);
    }
}
