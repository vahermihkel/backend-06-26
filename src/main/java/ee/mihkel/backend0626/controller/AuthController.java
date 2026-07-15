package ee.mihkel.backend0626.controller;

import ee.mihkel.backend0626.dto.LoginDto;
import ee.mihkel.backend0626.dto.PersonDTO;
import ee.mihkel.backend0626.entity.Person;
import ee.mihkel.backend0626.entity.PersonRole;
import ee.mihkel.backend0626.repository.PersonRepository;
import ee.mihkel.backend0626.security.JwtService;
import ee.mihkel.backend0626.service.SmartIdService;
import ee.mihkel.backend0626.util.Mapper;
import ee.mihkel.backend0626.util.ValidationUtil;
import ee.sk.smartid.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final PersonRepository personRepository;
    private final ValidationUtil validationUtil;
    private final JwtService jwtService;
    private final SmartIdService smartIdService;
    private final Mapper mapper;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findAll();
    }

    @GetMapping("person")
    public Person getPerson() {
        Long personId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return personRepository.findById(personId).orElseThrow();
    }

    @PostMapping("login")
    public String login(@RequestBody LoginDto loginDto) {
        if (loginDto.email().isBlank()) {
            throw new RuntimeException("Email cannot be empty");
        }
        Person person = personRepository.findByEmail(loginDto.email());
        if (person == null) {
            throw new RuntimeException("Cannot find user with that email");
        }
        if (loginDto.password().isBlank()) {
            throw new RuntimeException("Password cannot be empty");
        }
        if (!loginDto.password().equals(person.getPassword())) {
            throw new RuntimeException("Password does not match");
        }
        return jwtService.generateToken(person);
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        // kontrollid, et kõik sobib (email, parool jne)
        validationUtil.validateEmail(person.getEmail());
        validationUtil.validatePersonCode(person.getPersonCode());
        Person dbPerson = personRepository.findByEmail(person.getEmail());
        if (dbPerson != null) {
            throw new RuntimeException("Sellise emailiga kasutaja on juba olemas");
        }
        //person.setRole(PersonRole.CUSTOMER); Lives paneme tagasi peale
        return personRepository.save(person);
    }

    @PostMapping("smart-id")
    public String smartId() {
        return smartIdService.initiateSmartIdConnection();
    }

    @GetMapping("public-persons")
    public List<PersonDTO> getPublicPersons() {
//        List<Person> persons = personRepository.findAll();
//        List<PersonDTO> personDTOs = new ArrayList<>();
//        for (Person person: persons) {
//            PersonDTO personDTO = new PersonDTO(person.getFirstName(), person.getLastName());
//            personDTOs.add(personDTO);
//        }
//        return personDTOs;

        return  mapper.personsToPersonDTOs(personRepository.findAll());
    }
}
