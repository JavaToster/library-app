package org.example.DAO;

import org.example.util.PersonRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.example.models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person", new PersonRowMapper());
    }

    public void add(String name, int year){
        jdbcTemplate.update("INSERT INTO Person(name, borth_year) VALUES (?, ?)", name, year);
    }

    public Person show(int i){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{i}, new PersonRowMapper()).stream().findAny().orElse(null);
    }

    public void update(int id, String name, int year){
        jdbcTemplate.update("UPDATE Person SET name=?, borth_year=? WHERE id=?", name, year, id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public Person showPersonFromBook(int bookId){
        return jdbcTemplate.query("SELECT Person.id, Person.name, Person.borth_year FROM Person JOIN Books on Person.id = Books.owner_id WHERE Books.id=?", new Object[]{bookId}, new PersonRowMapper()).stream().findAny().orElse(null);
    }
}
