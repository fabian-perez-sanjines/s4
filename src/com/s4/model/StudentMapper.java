package com.s4.model;

import javax.xml.bind.annotation.XmlElement;

public class StudentMapper {
	private Long id;
	private String lastName;
	private String firstName;
	
	public StudentMapper(){		
	}

	public StudentMapper(Long id, String lastName, String firstName) {
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