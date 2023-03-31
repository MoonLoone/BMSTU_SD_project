package com.example.BMSTU_SD.backend.models;


import jakarta.persistence.*;

@Entity
@Table(name = "countries")
@Access(AccessType.FIELD)
public class Country {

    public Country() { }
    public Country(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, unique = true)
    public String name;

}
