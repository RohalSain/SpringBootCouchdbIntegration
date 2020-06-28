package com.restapi.couchdb.restapicouchdb.repository;

import com.restapi.couchdb.restapicouchdb.model.Person;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;


@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc="person",viewName="all")
public interface PersonRepository extends CouchbaseRepository<Person, Integer>{
    //add paging
}