package com.matias.spirngboot.app.datajpa.springbootdatajpa.repositories;

import org.springframework.data.repository.CrudRepository;

import com.matias.spirngboot.app.datajpa.springbootdatajpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

}
