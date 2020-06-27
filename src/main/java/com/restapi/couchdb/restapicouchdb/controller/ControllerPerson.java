package com.restapi.couchdb.restapicouchdb.controller;

import java.util.Optional;

import com.couchbase.client.core.annotations.InterfaceAudience.Public;
import com.restapi.couchdb.restapicouchdb.JwtUtil.JwtUtil;
import com.restapi.couchdb.restapicouchdb.model.AuthenticationRepsonse;
import com.restapi.couchdb.restapicouchdb.model.AuthenticationRequest;
import com.restapi.couchdb.restapicouchdb.model.Person;
import com.restapi.couchdb.restapicouchdb.repository.PersonRepository;
import com.restapi.couchdb.restapicouchdb.security.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ControllerPerson {

    @Autowired
    private PersonRepository person_repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping(value = "/addPerson",produces = "application/json")
    public Person getSavePerson(@RequestBody Person person) {
        // Person p = new Person(222, "Tushar", "Kapoor", "hsp", "Punjab");
        // person_repository.save(p);
        person_repository.save(person);
        return person;
    }

    @PutMapping(value = "/{udpatePerson}",consumes = "application/json")
    public Person getUpdatePerson(@RequestBody Person person) {
        person_repository.save(person);
        return  person;
    }

    @PatchMapping(value = "/updateAttributePerson/{id}",consumes = "application/json")
    public Person getUpdateAttributeOfPerson(@PathVariable("id") Integer id,@RequestBody Person person) {
        Person mperson = person_repository.findById(id).get();
        if(person.getFirstName() != null) {
            mperson.setFirstName(person.getFirstName());
        }

        if(person.getLastName() != null) {
            mperson.setLastName(person.getLastName());
        }

        if(person.getAddress() != null) {
            mperson.setAddress(person.getAddress());
        }

        if(person.getState() != null) {
            mperson.setState(person.getState());
        }

        return person_repository.save(mperson);
    }

    @GetMapping(value = "getAllPersons",produces = "application/json")
    public Iterable<Person> getAll() {
        return person_repository.findAll();
    }

    @DeleteMapping(value = "/deleteProduct/{id}",consumes = "application/json")
    public void deletePerson(@PathVariable("id") Integer id) {
        try {
        person_repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        }
    }

    @GetMapping(value = "findById/{id}",produces = "application/json")
    public ResponseEntity<Object> findById(@PathVariable("id") Integer id) {
        Optional<Person> optPerson = person_repository.findById(id);
         if(optPerson.isPresent()) {
            return new ResponseEntity<Object>(optPerson, HttpStatus.OK);
         } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
         }
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Username/Password error", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationRepsonse(jwt));
    }

}
