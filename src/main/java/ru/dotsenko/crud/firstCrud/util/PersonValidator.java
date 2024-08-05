package ru.dotsenko.crud.firstCrud.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dotsenko.crud.firstCrud.dao.PersonDAO;
import ru.dotsenko.crud.firstCrud.models.Person;

import static org.springframework.util.ClassUtils.isPresent;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.getPersonByFullName(person.getFullName()).isPresent())
    errors.rejectValue("fullName","","People with so FIO already");
    }
}
