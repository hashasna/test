package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {

    private String userId;
    private String username;
    private String passwordHash;
    private String accountNumber;

    public User(String transactiontypeid, String transactioncode, String transactionname) {
    }
}
