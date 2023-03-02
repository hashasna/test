package com.example.repository;

import com.example.exception.EtBadRequestException;
import com.example.exception.EtResourceNotFoundException;
import com.example.model.TransactionType;

import java.util.List;

public interface TransactionTypeServiceRepository {

    List<TransactionType> fetchAllCategories(String transactionTypeId);

    TransactionType fetchTrxTypeById(String transactionTypeId) throws EtResourceNotFoundException;

    TransactionType addTrxType(String transactionTypeId, String transactionTypeCode, String transactionTypeName) throws EtBadRequestException;

    void updateTrxType(String transactionTypeId, TransactionType transactionType) throws EtBadRequestException;

    void removeTrxType(String transactionTypeId) throws EtResourceNotFoundException;

}
