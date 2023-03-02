package com.example.service;

import com.example.Constant.Constant;
import com.example.model.User;
import com.example.repository.UserServiceRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserService {

    @Autowired
    UserServiceRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, Object> userMap){
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        User user = userRepository.validateUser(username, password);
        return new ResponseEntity<>(generateTokenJWT(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Map<String, Object> userMap){
        //String userId = (String) userMap.get("userId");
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("passwordHash");
        String accountNumber = (String) userMap.get("accountNumber");
        User user = userRepository.registerUser(username, password, accountNumber);
        return new ResponseEntity<>(generateTokenJWT(user), HttpStatus.OK);
    }

    private Map<String, String> generateTokenJWT(User user) {
        long timeStamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constant.API_SECRET_KEY)
                .setIssuedAt(new Date(timeStamp))
                .setExpiration(new Date(timeStamp + Constant.TOKEN_VALIDITY))
                .claim("userId", user.getUserId())
                .claim("username", user.getUsername())
                .claim("passwordHash", user.getPasswordHash())
                .claim("accountNumber", user.getAccountNumber())
                .compact();
        Map<String, String> mapGenerate = new HashMap<>();
        mapGenerate.put("token", token);
        return mapGenerate;
    }

}
