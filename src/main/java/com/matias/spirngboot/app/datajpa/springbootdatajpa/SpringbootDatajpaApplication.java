package com.matias.spirngboot.app.datajpa.springbootdatajpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

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

		// list();
		// findOde();
		// create();
		update();
	}

	@Transactional
	public void update(){
		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese id: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = repository.findOne(id);

		optionalPerson.ifPresent(p -> {
			// mostramos person
			System.out.println(p);
			// lenguaje nuevo
			System.out.println("Nuevo lenguaje: ");
			String newLanguage = scanner.next();
			// se lo asignamos
			p.setProgrammingLanguage(newLanguage);
			// lo guardamos
			Person personDB = repository.save(p);
			System.out.println(personDB);
		});
	}

	@Transactional
	public void create(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Nombre: ");
		String name = scanner.next();
		System.out.println("Apellido: ");
		String lastName = scanner.next();
		System.out.println("Lneguaje de programacion: ");
		String programmingLanguage = scanner.next();

		Person person = new Person(null, name, lastName, programmingLanguage);

		Person newPerson = repository.save(person);
		System.out.println(newPerson);

		repository.findById(newPerson.getId()).ifPresent(System.out::println);
	}

	// se anota asi los métodos que no modifican la lista
	@Transactional(readOnly = true)
	public void findOde(){
		Person person = null;
		Optional<Person> optionalPerson = repository.findOne(1L);
		// esto es en caso de que la persona no exista, con esto lanza una excepcion
		String namePersona = optionalPerson.get().getLastName();
		// validamos si esta presente y la mostramos
		if(optionalPerson.isPresent()){
			person = optionalPerson.get();
		}
		System.out.println(person);

		// esta linea sola hace lo mismo que todo lo de arriba
		repository.findOne(2L).ifPresent(persons -> System.out.println(persons));
		// Busqueda de uno por nombre
		repository.findOneName("Giuliano").ifPresent(persons -> System.out.println(persons));
		// buscamos un usuario por una coinsidencia en el nombre, es decir, devuelve en base a si encuentra algo parecido para adelante y atras
		repository.findOneLikeName("Ju").ifPresent(System.out::println);
		// es igual que el de arriba pero ya viene integrado
		repository.findByNameContaining("qui").ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void list(){

		List<Person> persons = (List<Person>) repository.findAll();
		System.out.println("\n Estos solo son todos ");
		persons.stream().forEach(person -> System.out.println(person));

		List<Person> personsByLanguage = (List<Person>) repository.buscarByProgrammingLanguage("Java");
		System.out.println("\n Estos solo son los de JAVA");
		personsByLanguage.stream().forEach(person -> System.out.println(person));

		List<Person> personsByLanguageAndName = (List<Person>) repository.buscarByProgrammingLanguageAndName("Java", "Matias");
		System.out.println("\n Estos son los de JAVA con nombre Matias");
		personsByLanguageAndName.stream().forEach(person -> System.out.println(person));

		List<Object[]> personValues = repository.obtenerPersonData();
		System.out.println("\n Datos de las personas");
		personValues.stream().forEach(person -> System.out.println(person[0] + " " + person[1] +  " es exterto en " + person[2]));

		List<Object[]> personValuesByProgrammingLanguage = repository.obtenerPersonDataByProgrammingLanguage("Java");
		System.out.println("\n Filtrar por lenguaje");
		personValuesByProgrammingLanguage.stream().forEach(person -> System.out.println(person[0] + " es experto/a en " + person[1]));

	}
}
