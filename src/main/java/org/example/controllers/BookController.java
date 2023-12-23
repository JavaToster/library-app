package org.example.controllers;

import org.example.DAO.BookDAO;
import org.example.DAO.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Autowired
    public void setPersonDAO(PersonDAO personDAO){ this.personDAO = personDAO;};

    @GetMapping()
    public String index(Model model, @ModelAttribute("book") Book book){
        model.addAttribute("books", bookDAO.index());

        return "books";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());

        return "newBook";
    }

    @PostMapping("/new")
    public String postNew(@ModelAttribute("book") Book book){
        bookDAO.add(book.getName(), book.getAuthor(), book.getYear());

        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("personBooks", personDAO.showPersonFromBook(id));
        model.addAttribute("person", new Person());
        model.addAttribute("people", personDAO.index());

        return "showBook";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));

        return "editBook";
    }

    @PatchMapping("/{id}/edit")
    public String patchEditBook(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookDAO.update(id, book.getName(), book.getAuthor(), book.getYear());

        return "redirect:/books";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.delete(id);

        return "redirect:/books";
    }

    @PostMapping("/{id}/addBookForPerson")
    public String addBookForPerson(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDAO.setBookForPerson(id, person.getId());

        return "redirect:/books";
    }

    @PostMapping("/{id}/clear")
    public String clear(@PathVariable("id") int id){
        bookDAO.clear(id);

        return "redirect:/books";
    }
}
