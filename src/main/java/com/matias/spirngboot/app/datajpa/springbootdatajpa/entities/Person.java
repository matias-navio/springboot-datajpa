package com.matias.spirngboot.app.datajpa.springbootdatajpa.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// toda clase de JPA, tiene que estar anotada con Entity para indicar que es una clase de persistencia
@Entity
@Table(name = "persons")
public class Person {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lasrName;
    private String programmingLanguage;
}
