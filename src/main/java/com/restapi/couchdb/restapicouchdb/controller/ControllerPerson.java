package com.restapi.couchdb.restapicouchdb.controller;

import com.restapi.couchdb.restapicouchdb.JwtUtil.JwtUtil;
import com.restapi.couchdb.restapicouchdb.model.AuthenticationRepsonse;
import com.restapi.couchdb.restapicouchdb.model.AuthenticationRequest;
import com.restapi.couchdb.restapicouchdb.model.Person;
import com.restapi.couchdb.restapicouchdb.repository.PersonRepository;
import com.restapi.couchdb.restapicouchdb.security.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value = "home")
    public String getSavePerson() {
        Person p = new Person(111, "Tushar", "Kapoor", "hsp", "Punjab");
        person_repository.save(p);
        return "data is saved";
    }

    @GetMapping(value = "allpersons")
    public Iterable<Person> getAll() {
        return person_repository.findAll();
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World ";
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
