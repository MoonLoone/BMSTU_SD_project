package com.example.BMSTU_SD.backend.controllers;

import com.example.BMSTU_SD.backend.models.Artist;
import com.example.BMSTU_SD.backend.models.Country;
import com.example.BMSTU_SD.backend.repositories.ArtistRepository;
import com.example.BMSTU_SD.backend.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@CrossOrigin("")
@RestController
@RequestMapping("/api/v1")
public class CountryController {
    @Autowired
    CountryRepository countryRepository;

    @GetMapping("/countries/{id}/artists")
    public ResponseEntity<List<Artist>> getCountryArtists(@PathVariable(value = "id") Long countryId) {
        Optional<Country> cc = countryRepository.findById(countryId);
        return cc.map(country -> ResponseEntity.ok(country.artists)).orElseGet(() -> ResponseEntity.ok(new ArrayList<>()));
    }

    @GetMapping("/countries")
    public Page<Country> getAllCountries(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return countryRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "name")));
    }

    @PostMapping("/countries")
    public ResponseEntity<Object> createCountry(@RequestBody Country country)
            throws Exception {
        try {
            Country nc = countryRepository.save(country);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("countries.name_UNIQUE"))
                error = "countyalreadyexists";
            else
                error = "undefinederror";
            Map<String, String>
                    map = new HashMap<>();
            map.put("error", error);
            return ResponseEntity.ok(map);
        }
    }


    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") Long countryId,
                                                 @RequestBody Country countryDetails) {
        Country country = null;
        Optional<Country> cc = countryRepository.findById(countryId);
        if (cc.isPresent()) {
            country = cc.get();
            country.name = countryDetails.name;
            countryRepository.save(country);
            return ResponseEntity.ok(country);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "country not found");
        }
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long countryId) {
        Optional<Country>
                country = countryRepository.findById(countryId);
        Map<String, Boolean>
                resp = new HashMap<>();
        if (country.isPresent()) {
            countryRepository.delete(country.get());
            resp.put("deleted", Boolean.TRUE);
        } else
            resp.put("deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/deletecountries")
    public ResponseEntity<HttpStatus> deleteCountries(@RequestBody List<Country> countries) {
        countryRepository.deleteAll(countries);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}