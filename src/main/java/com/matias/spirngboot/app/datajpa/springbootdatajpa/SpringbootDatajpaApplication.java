package com.matias.spirngboot.app.datajpa.springbootdatajpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matias.spirngboot.app.datajpa.springbootdatajpa.entities.Person;
import com.matias.spirngboot.app.datajpa.springbootdatajpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootDatajpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootDatajpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Person> persons = (List<Person>) repository.findAll();
		persons.stream().forEach(person -> System.out.println(person.getName()));
	}
}
