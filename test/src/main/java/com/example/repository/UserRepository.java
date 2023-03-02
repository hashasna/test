package com.example.repository;

import com.example.model.User;

public interface UserRepository {

    User findByUsernameAndPassword(String username, String password);

    User findById(String userId);

}
