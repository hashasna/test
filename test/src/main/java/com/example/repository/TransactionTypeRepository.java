package com.example.repository;

import com.example.exception.EtBadRequestException;
import com.example.exception.EtResourceNotFoundException;
import com.example.model.TransactionType;
import com.example.model.User;

import java.util.List;

public interface TransactionTypeRepository {

    List<User> findAll(String transactionTypeId) throws EtResourceNotFoundException;

    TransactionType findByTypeId(String transactionTypeId) throws EtResourceNotFoundException;

    String createdTrxType(String transactionTypeId, String transactionCode, String transactionName) throws EtBadRequestException;

    void update(String transactionTypeId, String transactionCode, TransactionType transactionType) throws EtBadRequestException;

    void removeById(String transactionTypeId);

}
