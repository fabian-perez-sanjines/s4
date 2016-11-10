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
	private String lastname;
	private String firstname;

	public StudentMapper() {
	}

	public StudentMapper(Long id, String firstname, String lastname) {
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public Long getId() {
		return id;
	}

	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	@XmlElement
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	@XmlElement
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

}