package com.example.FirstProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.processing.Generated;
@Entity(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

private Long categoryid;
private String categoryname;


}
