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
	private @Index String firstName;
	private @Index String lastName;
	private List<Key<ClassEntity>> classes;

	public StudentEntity(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.classes = new ArrayList<Key<ClassEntity>>();
	}

	public StudentEntity(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Key<ClassEntity>> getClasses() {
		return classes;
	}

	public void setClasses(List<Key<ClassEntity>> classes) {
		this.classes = classes;
	}

}