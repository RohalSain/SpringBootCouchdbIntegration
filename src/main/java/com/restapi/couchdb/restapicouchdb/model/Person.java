package com.restapi.couchdb.restapicouchdb.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import org.springframework.data.couchbase.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Person {
	@Id
	private Integer id;
	@Field
	private String firstName;
	@Field
    private String lastName;
    @Field
    private String address;
    @Field
    private String state;
    

}