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
@RequestMapping("/people")
public class PersonController {
    private PersonDAO personDAO;
    private BookDAO bookDAO;

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Autowired
    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String people(Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("people", personDAO.index());

        return "people";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());

        return "new";
    }

    @PostMapping("/new")
    public String addPerson(@ModelAttribute("person") Person person){
        personDAO.add(person.getName(), person.getYear());

        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));

        return "edit";
    }

    @PatchMapping("/{id}/edit")
    public String postEdit(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        personDAO.update(id, person.getName(), person.getYear());

        return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("personBooks", bookDAO.indexBooksForPerson(id));
        model.addAttribute("book", new Book());

        return "show";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);

        return "redirect:/people";
    }
}
