package com.example.BMSTU_SD.backend.controllers;

import com.example.BMSTU_SD.backend.models.Artist;
import com.example.BMSTU_SD.backend.models.Country;
import com.example.BMSTU_SD.backend.repositories.ArtistRepository;
import com.example.BMSTU_SD.backend.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ArtistsController {

    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    CountryRepository countryRepository;

    @GetMapping("/artists")
    public List
    getAllArtists() {
        return artistRepository.findAll();
    }

    @PostMapping("/artists")
    public ResponseEntity<Object> createArtist(@RequestBody Artist artist) throws Exception {
        try {
            Optional<Country>
                    cc = countryRepository.findById(artist.country.id);
            if (cc.isPresent()) {
                artist.country = cc.get();
            }
            Artist nc = artistRepository.save(artist);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);

        }
        catch(Exception ex) {
            String error = "undefinederror";
            Map<String, String> map =  new HashMap<>();
            map.put("error", error);
            return new ResponseEntity<Object> (map, HttpStatus.OK);
        }
    }
}
