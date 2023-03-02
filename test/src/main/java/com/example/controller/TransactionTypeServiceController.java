package com.example.controller;

import com.example.exception.EtBadRequestException;
import com.example.exception.EtResourceNotFoundException;
import com.example.model.TransactionType;
import com.example.repository.TransactionTypeServiceRepository;

import java.util.List;

public class TransactionTypeServiceController implements TransactionTypeServiceRepository {
    @Override
    public List<TransactionType> fetchAllCategories(String transactionTypeId) {
        return null;
    }

    @Override
    public TransactionType fetchTrxTypeById(String transactionTypeId) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public TransactionType addTrxType(String transactionTypeId, String transactionTypeCode, String transactionTypeName) throws EtBadRequestException {
        return null;
    }

    @Override
    public void updateTrxType(String transactionTypeId, TransactionType transactionType) throws EtBadRequestException {

    }

    @Override
    public void removeTrxType(String transactionTypeId) throws EtResourceNotFoundException {

    }
}
