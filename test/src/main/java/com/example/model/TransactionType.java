package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TransactionType {

    private String transactionTypeId;
    private String transactionCode;
    private String transactionName;
    private String userId;
}
