package com.example.BMSTU_SD.backend.repositories;

import com.example.BMSTU_SD.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
