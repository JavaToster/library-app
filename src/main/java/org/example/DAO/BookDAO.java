package org.example.DAO;

import org.example.models.Book;
import org.example.util.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Books", new BookRowMapper());
    }

    public List<Book> indexBooksForPerson(int id){
        List<Book> bookList = jdbcTemplate.query("SELECT Books.id, Books.name, Books.author, Books.year FROM Books JOIN Person ON Person.id = Books.owner_id WHERE Person.id=?", new Object[]{id}, new BookRowMapper());
        return bookList;
    }

    public Book show(int id){
        return jdbcTemplate.query("SELECT * FROM Books WHERE id=?", new Object[]{id}, new BookRowMapper()).stream().findAny().orElse(null);
    }

    public void add(String name, String author, int year){
        jdbcTemplate.update("INSERT INTO Books(name, author, year, owner_id) VALUES (?, ?, ?, -1)", name, author, year);
    }

    public void update(int id, String name, String author, int year){
        jdbcTemplate.update("UPDATE Books SET name=?, author=?, year=? WHERE id=?", name, author,year, id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM Books WHERE id=?", id);
    }

    public void setBookForPerson(int id, int personId){
        jdbcTemplate.update("UPDATE Books SET owner_id=? WHERE id=?", personId, id);
    }

    public void clear(int id){
        jdbcTemplate.update("UPDATE Books SET owner_id = -1 WHERE id=?", id);
    }
}
