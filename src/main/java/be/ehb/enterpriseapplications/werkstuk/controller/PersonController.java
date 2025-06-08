package be.ehb.enterpriseapplications.werkstuk.controller;

import be.ehb.enterpriseapplications.werkstuk.model.Person;
import be.ehb.enterpriseapplications.werkstuk.repository.PersonRepository;
import be.ehb.enterpriseapplications.werkstuk.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
     List<Person> findAll(@RequestParam(name = "search", required = false) String searchName) {
        if (searchName != null) {
            return personService.findByName(searchName);
        }
        return personService.findAll();
    }

    @PostMapping
    Person save(@Valid @RequestBody Person person) {
        return personService.save(person);
    }

    @GetMapping("/{id}")
    Person findById(@PathVariable String id) {
        return personService.findById(id);
    }
}
