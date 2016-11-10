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
public class ClassMapper {
	private Long code;
	private String title;
	private String description;

	public ClassMapper() {
	}

	public ClassMapper(Long code, String title, String description) {
		this.code = code;
		this.title = title;
		this.description = description;
	}

	public Long getCode() {
		return code;
	}

	@XmlElement
	public void setCode(Long code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
}