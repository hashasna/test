package com.example.controller;

import com.example.exception.EtBadRequestException;
import com.example.exception.EtResourceNotFoundException;
import com.example.model.TransactionType;
import com.example.model.User;
import com.example.repository.TransactionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class TransactionTypeRepositoryController implements TransactionTypeRepository {

    private static final String SQL_FIND_ALL = "SELECT * FROM TRANSACTIONTYPE";
    private static final String SQL_CREATE = "INSERT INTO TRANSACTIONTYPE (TRANSACTIONID, TRANSACTIONCODE, " +
            "TRANSACTIONNAME) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE TRANSACTIONTYPE SET TRANSACTIONCODE = ?, TRANSACTIONNAME = ?" +
            "WHERE TRANSACTIONID = ?";
    private static final String SQL_DELETE_TRANSACTIONTYPE = "DELETE FROM TRANSACTIONTYPE WHERE  TRANSACTIONID = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll(String transactionTypeId) throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL, transactionTypeRowMapperr, new Object[]{transactionTypeId});
    }

    @Override
    public TransactionType findByTypeId(String transactionTypeId) throws EtResourceNotFoundException {
        return null;
    }

    @Override
    public String createdTrxType(String transactionTypeId, String transactionCode, String transactionName) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, transactionTypeId);
                ps.setString(2, transactionCode);
                ps.setString(3, transactionName);
                return ps;
            }, keyHolder);
            return (String) keyHolder.getKeys().get("transactionTypeId");
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(String transactionTypeId, String transactionCode, TransactionType transactionType) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{transactionType.getTransactionCode(), transactionType.getTransactionName(), transactionTypeId});
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(String transactionTypeId) {
        jdbcTemplate.update(SQL_DELETE_TRANSACTIONTYPE, new Object[]{transactionTypeId});
    }

    private RowMapper<User> transactionTypeRowMapperr = ((rs, rowNum) -> {
        return new User(
                rs.getString("TRANSACTIONTYPEID"),
                rs.getString("TRANSACTIONCODE"),
                rs.getString("TRANSACTIONNAME"));
    });
}
