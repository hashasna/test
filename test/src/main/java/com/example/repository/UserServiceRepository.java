package com.example.repository;

import com.example.exception.EtAuthException;
import com.example.model.User;

public interface UserServiceRepository {

    User validateUser(String username, String password) throws EtAuthException;

    User registerUser(String username, String passwordHash, String accountNumber);
}
