package com.example.BMSTU_SD.backend.controllers;

import com.example.BMSTU_SD.backend.models.User;
import com.example.BMSTU_SD.backend.repositories.UserRepository;
import com.example.BMSTU_SD.backend.tools.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> credentials) {
        String login = credentials.get("login");
        String pwd = credentials.get("password");
        if (!pwd.isEmpty() && !login.isEmpty()) {
            Optional<User> uu = userRepository.findByLogin(login);
            if (uu.isPresent()) {
                User u2 = uu.get();
                String hash1 = u2.password;
                String salt = u2.salt;
                String hash2 = Utils.ComputeHash(pwd, salt);

                if (hash1.toLowerCase().equals(hash2.toLowerCase())) {
                    String token = UUID.randomUUID().toString();
                    u2.token = token;
                    u2.activity = LocalDateTime.now();
                    User u3 = userRepository.saveAndFlush(u2);


                    String log = String.valueOf(u3.login);
                    String em = String.valueOf(u3.email);
                    String mus = String.valueOf(u3.museums);
                    String tok = String.valueOf(u3.token);
                    Map<String, String> rm = new HashMap<>();
                    rm.put("login", log);
                    rm.put("email", em);
                    rm.put("token", tok);
                    rm.put("museums", mus);


                    return new ResponseEntity<Object>(rm, HttpStatus.OK);
                }

            }
        }
        return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
    }
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && !token.isEmpty()) {
            token = StringUtils.removeStart(token, "Bearer").trim();
            Optional uu = userRepository.findByToken(token);
            if (uu.isPresent()) {
                User u = (User) uu.get();
                u.token = null;
                userRepository.save(u);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
