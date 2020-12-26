package com.javaproject.movies.service.user;

import com.javaproject.movies.entity.user.User;

import java.util.List;

public interface UserService {

    User register(User user);

    User save(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findByUsernameOrNull(String username);

    User findById(Long id);

    void delete(Long id);
}
