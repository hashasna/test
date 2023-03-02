package com.example.controller;

import com.example.exception.EtAuthException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserRepositoryController implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO USERS(USERID, USERNAME, PASSWORDHASH, ACCOUNTNUMBER) " +
            "VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_BY_USERNAME = "SELECT USERID, USERNAME, PASSWORD, ACCOUNTNUMBER " +
            "FROM USERS WHERE USERNAME = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USERID, USERNAME, PASSWORD, ACCOUNTNUMBER " +
            "FROM USERS WHERE USERID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findByUsernameAndPassword(String username, String password) throws EtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_USERNAME, userRowMapper, username);
            if(!BCrypt.checkpw(password, user.getPasswordHash()))
                throw new EtAuthException("Invalid username/password");
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new EtAuthException("Invalid username/password");
        }
    }

    @Override
    public User findById(String userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, userRowMapper, userId);
    }

    @Override
    public String createdUser(String username, String passwordHash, String accountNumber) {
        String hashedPassword = BCrypt.hashpw(passwordHash, BCrypt.gensalt(10));
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, accountNumber);
                ps.setString(3, hashedPassword);
                return ps;
            }, keyHolder);
            return (String) keyHolder.getKeys().get("USERID");
        }catch (Exception e) {
            throw new EtAuthException("Invalid details. Failed to create account");
        }
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(rs.getString("USERID"),
                rs.getString("USERNAME"),
                rs.getString("PASSWORDHASH"),
                rs.getString("ACCOUNTNUMBER"));
    });
}
