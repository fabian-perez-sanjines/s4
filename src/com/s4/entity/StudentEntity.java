package com.s4.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Entity student that represents a Student to be stored in the datastore
 * 
 * @author Fabian Perez
 * 
 */
@Entity
public class StudentEntity {
	private @Id Long id;
	private @Index String firstname;
	private @Index String lastname;
	private List<Key<ClassEntity>> classes;

	public StudentEntity(String firstName, String lastname) {
		this.firstname = firstName;
		this.lastname = lastname;
		this.classes = new ArrayList<Key<ClassEntity>>();
	}

	public StudentEntity(Long id, String firstname, String lastname) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.classes = new ArrayList<Key<ClassEntity>>();
	}

	public StudentEntity() {
		this.classes = new ArrayList<Key<ClassEntity>>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Key<ClassEntity>> getClasses() {
		return classes;
	}

	public void setClasses(List<Key<ClassEntity>> classes) {
		this.classes = classes;
	}

}