package com.s4.entity;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class ClassEntity {
	private @Id Long code;
	private @Index String title;
	private @Index String description;
	private List<Key<StudentEntity>> students;
    
	public ClassEntity(String title, String description) {
		this.title = title;
		this.description = description;
		this.students = new ArrayList<Key<StudentEntity>>();
	}
	
	public ClassEntity(Long code, String title, String description) {
		this.code = code;
		this.title = title;
		this.description = description;
		this.students = new ArrayList<Key<StudentEntity>>();
	}

	public ClassEntity() {
		this.students = new ArrayList<Key<StudentEntity>>();
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Key<StudentEntity>> getStudents() {
		return students;
	}

	public void setStudents(List<Key<StudentEntity>> students) {
		this.students = students;
	}
}