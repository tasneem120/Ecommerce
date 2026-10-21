package com.example.FirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    @Size(min = 5, message = "Street name must be at least 5 character")
    private String street;
    @Size(min = 5, message = "City name must be at least 5 character")
    private String city;
    @Size(min = 2, message = "State name must be at least 2 character")
    private String state;
    @Size(min = 6, message = "ZipCode must be at least 6 character")
    private String zip;
    @Size(min = 5, message = "Country name must be at least 5 character")
    private String country;
    @Size(min = 5, message = "Building name must be at least 5 character")
    private String buildingName;
    @ManyToOne

    @JoinColumn(name = "user_id")
    private  User  user;

}
