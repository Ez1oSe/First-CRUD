package ru.dotsenko.crud.firstCrud.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dotsenko.crud.firstCrud.dao.PersonDAO;
import ru.dotsenko.crud.firstCrud.models.Person;
import ru.dotsenko.crud.firstCrud.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
        private final PersonDAO personDAO;
        private final PersonValidator personValidator;

        @Autowired
        public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
            this.personDAO = personDAO;
            this.personValidator = personValidator;
        }
     //Показать список людей - работает
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    //Показать выбранного человека - работает
        @GetMapping("/{id}")
        public String show(@PathVariable("id") int id, Model model) {
            model.addAttribute("person", personDAO.show(id));
            model.addAttribute("books", personDAO.getBookByPersonId(id));
            return "people/show";
        }

        //Создание нового человека - работает
     @GetMapping("/new")
        public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
          }

        @PostMapping()
     public String create(@ModelAttribute("person") @javax.validation.Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }
    //Редактирование человека - работает
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(id, person);
        return "redirect:/people";
    }
    //Удаление человека - работае
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
    }


