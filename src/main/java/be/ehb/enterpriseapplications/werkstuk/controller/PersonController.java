package be.ehb.enterpriseapplications.werkstuk.controller;

import be.ehb.enterpriseapplications.werkstuk.model.Person;
import be.ehb.enterpriseapplications.werkstuk.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    ResponseEntity<Person> save(@Valid @RequestBody Person person) {
        personService.save(person);
        return ResponseEntity.ok(person);
    }

    @GetMapping("/{id}")
    Optional<Person> findById(@PathVariable String id) {
        return personService.findById(id);
    }
}
