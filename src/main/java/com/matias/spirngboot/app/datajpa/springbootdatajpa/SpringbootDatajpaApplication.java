package com.matias.spirngboot.app.datajpa.springbootdatajpa;

import java.util.Iterator;
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
		// update();
		// delete();
		// delete2();
		personaliceQueries();
	}

	@Transactional(readOnly = true)
	public void personaliceQueries(){
		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese el id del usuario: ");
		Long id = scanner.nextLong();

		System.out.println("========== Devolvemos el nombre ==========");
		String name = repository.getNameById(id);
		System.out.println("El usuario con ID " + id + " es " + name);

		System.out.println("========== Devolvemos el nombre y lenguaje ==========");
		String lenguaje = repository.getProgrammingLanguageById(id);
		System.out.println("El lenguaje favorito de " + name + " es " + lenguaje);

		System.out.println("========== Devolvemos el nombre completo ==========");
		String fullName = repository.getFullNameById(id);
		System.out.println("Nombre completo del ID " + id + ": " + fullName);

		System.out.println("========== Devolvemos toda la data de un usuario por el id ==========");
		Optional<Object> personOp = repository.getDataPersonById(id);
		if(personOp.isPresent()){
			Object[] personReg = (Object[])personOp.get();
			System.out.println("Id: " + personReg[0] + " Nombre: " + personReg[1] + " Apellido: " + personReg[2] + " Lenguaje: " + personReg[3]);
		}
		
		System.out.println("========== Devolvemos todos los datos con un iterator ==========");
		Iterator<Object[]> personDataList = repository.getDataPersonList().iterator();
		while (personDataList.hasNext()) {
			personDataList.forEachRemaining(p -> 
							System.out.println("Id: " + p[0] + 
											" Nombre: " + p[1] + 
											" Apellido: " + p[2] + 
											" Lenguaje: " + p[3]));
		}
	}

	@Transactional
	public void delete2(){
		Scanner scanner = new Scanner(System.in);

		System.out.println("Listado de personas: ");
		repository.findAll().forEach(System.out::println);

		System.out.println("Ingrese el id para eliminar: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);
		optionalPerson.ifPresentOrElse(p -> repository.delete(p), () -> System.out.println("La persona no existe en la base de datos!"));

		System.out.println("Listado nuevo de personas: ");
		repository.findAll().forEach(System.out::println);
	}

	@Transactional
	public void delete(){
		Scanner scanner = new Scanner(System.in);

		System.out.println("Listado de personas: ");
		repository.findAll().forEach(System.out::println);

		System.out.println("Ingrese id para eliminar: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = repository.findOne(id);

		if(optionalPerson.isPresent()){
			repository.deleteById(id);
			System.out.println("Eliminado con exito!");
			repository.findAll().forEach(System.out::println);
		}else{
			System.out.println("Persona no encontrada en la base de datos!");
		}
	}

	@Transactional
	public void update(){
		Scanner scanner = new Scanner(System.in);

		System.out.println("Ingrese id: ");
		Long id = scanner.nextLong();
		Optional<Person> optionalPerson = repository.findOne(id);

		optionalPerson.ifPresent(pDb -> {
			// mostramos person
			System.out.println(pDb);
			// lenguaje nuevo
			System.out.println("Nuevo lenguaje: ");
			String newLanguage = scanner.next();
			// se lo asignamos
			pDb.setProgrammingLanguage(newLanguage);
			// lo guardamos
			Person personUpdate = repository.save(pDb);
			System.out.println(personUpdate);
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
		repository.findOne(2L).ifPresent(System.out::println);
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
