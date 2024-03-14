package com.matias.spirngboot.app.datajpa.springbootdatajpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.matias.spirngboot.app.datajpa.springbootdatajpa.dto.PersonDto;
import com.matias.spirngboot.app.datajpa.springbootdatajpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

    // la consulta personalizada se hace sobre la clase entity, y no sobre la tabla
    @Query("select p from Person p where p.programmingLanguage = ?1")
    List<Person> buscarByProgrammingLanguage(String prograamingLanguage);

    @Query("select p from Person p where p.programmingLanguage = ?1 and p.name= ?2")
    List<Person> buscarByProgrammingLanguageAndName(String prograamingLanguage, String name);

    @Query("select p.name, p.lastname, p.programmingLanguage from Person p")
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

    @Query("select concat(p.name, ' ', p.lastname) from Person p where p.id = ?1")
    String getFullNameById(Long id);

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> getDataPersonList();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id = ?1")
    Optional<Object> getDataPersonById(Long id);

    @Query("select new com.matias.spirngboot.app.datajpa.springbootdatajpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonDto();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct(p.name) from Person p")
    List<String> findAllNamesDistinct();

    @Query("select distinct(p.programmingLanguage) from Person p")
    List<String> findAllLanguagesDisctinct();

    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long findAllLanguagesDisctinctCount();

    @Query("select concat(p.name, ' ', p.lastname) from Person p")
    List<String> findFullNameConcat();

    @Query("select upper(concat(p.name, ' ' , p.lastname)) from Person p")
    List<String> findFullNameConcatUpper();

    @Query("select lower(concat(p.name, ' ', p.lastname)) from Person p")
    List<String> findFullNameConcatLower();

    @Query("select p.id, upper(p.name), lower(p.lastname), upper(p.programmingLanguage) from Person p")
    List<Object[]> findPersonDataConcatMix();

    @Query(value = "select * from persons where id between ?1 and ?2 order by name desc", nativeQuery = true)
    List<Person> findAllBetweenId(Integer id1, Integer id2);

    @Query(value = "select * from persons where name between ?1 and ?2 order by name asc", nativeQuery = true)
    List<Person> findAllByNameBetween(String c1, String c2);

    @Query(value = "select * from persons where name like ?1 order by id desc", nativeQuery = true)
    List<Person> findAllByNameLikeDesc(String name1);

    List<Person> findByNameLikeOrderByIdDesc(String name);

    List<Person> findByIdBetweenOrderByNameAsc(Long id1, Long id2);

    List<Person> findByNameBetweenOrderByNameDesc(String name1, String name2);
}
