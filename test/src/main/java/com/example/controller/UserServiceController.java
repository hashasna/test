package com.example.controller;

import com.example.exception.EtAuthException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceController implements UserServiceRepository {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String username, String password) throws EtAuthException {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User registerUser(String username, String passwordHash, String accountNumber) {
        String userId = userRepository.createdUser(username, passwordHash, accountNumber);
        return userRepository.findById(userId);
    }
}
