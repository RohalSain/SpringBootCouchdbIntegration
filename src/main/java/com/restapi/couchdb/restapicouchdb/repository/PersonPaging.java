package com.restapi.couchdb.restapicouchdb.repository;

import java.util.List;

import com.restapi.couchdb.restapicouchdb.model.Person;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@N1qlPrimaryIndexed
@ViewIndexed(designDoc="person",viewName="all")
public interface PersonPaging extends CouchbasePagingAndSortingRepository<Person, String> {
 
    @Query("#{#n1ql.selectEntity} WHERE #{#n1ql.filter} order by id asc limit $1 offset $2 ")
    List<Person> listPersons( Integer limit, Integer offset);
    }
