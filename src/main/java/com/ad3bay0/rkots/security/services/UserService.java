package com.ad3bay0.rkots.security.services;

import java.util.Optional;

import com.ad3bay0.rkots.models.User;

public interface UserService {

    void save(User user);

    Optional<User> findByUsername(String username);
}