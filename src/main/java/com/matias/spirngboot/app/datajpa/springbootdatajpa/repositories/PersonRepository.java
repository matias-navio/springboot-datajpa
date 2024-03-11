package com.matias.spirngboot.app.datajpa.springbootdatajpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matias.spirngboot.app.datajpa.springbootdatajpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

    // la consulta personalizada se hace sobre la clase entity, y no sobre la tabla
    @Query("select p from Person p where p.programmingLanguage = ?1")
    List<Person> buscarByProgrammingLanguage(String prograamingLanguage);

    @Query("select p from Person p where p.programmingLanguage = ?1 and p.name= ?2")
    List<Person> buscarByProgrammingLanguageAndName(String prograamingLanguage, String name);

    @Query("select p.name, p.lastName, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.id = ?1")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name = ?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    // este es igual al de arriba, pero este ya está integrado
    Optional<Person> findByNameContaining(String name);

    // consulta personalizada para solo obtener el nombre mediante un id
    @Query("select p.name from Person p where p.id = ?1")
    String getNameById(Long id);

    @Query("select p.programmingLanguage from Person p where p.id = ?1")
    String getProgrammingLanguageById(Long id);

    @Query("select concat(p.name, ' ', p.lastName) from Person p where p.id = ?1")
    String getFullNameById(Long id);

    @Query("select p.id, p.name, p.lastName, p.programmingLanguage from Person p")
    List<Object[]> getDataPersonList();

    @Query("select p.id, p.name, p.lastName, p.programmingLanguage from Person p where p.id = ?1")
    Optional<Object> getDataPersonById(Long id);

}
