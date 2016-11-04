package com.s4.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Mapping class that represents a Class, is used to map to JSON.
 * 
 * @author Fabian Perez
 * 
 */
@XmlRootElement
public class StudentMapper {
	private Long id;
	private String lastName;
	private String firstName;

	public StudentMapper() {
	}

	public StudentMapper(Long id, String firstName, String lastName) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}