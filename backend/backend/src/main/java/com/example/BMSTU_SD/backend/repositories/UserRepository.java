package com.example.BMSTU_SD.backend.repositories;

import com.example.BMSTU_SD.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByToken(String token);

    Optional<User> findByLogin(String login);

}
