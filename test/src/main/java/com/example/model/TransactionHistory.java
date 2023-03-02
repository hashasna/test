package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TransactionHistory {

    private String transactionHistoryId;
    private String activityDate;
    private String amount;
    private String userId;
    private String transactionTypeId;

}
