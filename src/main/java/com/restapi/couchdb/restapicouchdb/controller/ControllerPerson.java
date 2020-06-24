package com.restapi.couchdb.restapicouchdb.controller;

import com.restapi.couchdb.restapicouchdb.model.Person;
import com.restapi.couchdb.restapicouchdb.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class ControllerPerson {
    
    @Autowired
    private PersonRepository person_repository;
    
    @GetMapping(value="home")
    public String getSavePerson() {
        Person p= new Person();
        p.setId(111);
        p.setFirstName("Tuhsal");
        p.setLastName("kapoor");
        p.setAddress("Hoshiarpur");
        p.setState("Punjab");
        person_repository.save(p);
        return  "data is saved";
    }

    @GetMapping(value = "allpersons")
    public Iterable<Person> getAll() {
		return person_repository.findAll();
	}
    
}