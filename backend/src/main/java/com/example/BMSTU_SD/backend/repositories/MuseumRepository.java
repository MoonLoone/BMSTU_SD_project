package com.example.BMSTU_SD.backend.repositories;

import com.example.BMSTU_SD.backend.models.Museum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MuseumRepository extends JpaRepository<Museum, Long> {
}
