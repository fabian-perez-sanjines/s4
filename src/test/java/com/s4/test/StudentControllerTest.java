package com.s4.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.s4.controller.StudentController;
import com.s4.entity.StudentEntity;
import com.s4.model.StudentMapper;

public class StudentControllerTest {

	@Test
	public void testGetMapperPositive() {
		StudentEntity studentEntity = new StudentEntity(1L, "John", "Smith");
		StudentMapper result = StudentController.getMapper(studentEntity);
		assertEquals(1L, (long) result.getId());
		assertEquals("John", result.getFirstName());
		assertEquals("Smith", result.getLastName());
	}

	@Test
	public void testGetMapperNegative() {
		StudentMapper result = StudentController.getMapper(null);
		assertEquals(null, result);
	}

	@Test
	public void testGetEntityPositive() {
		StudentMapper studentMapper = new StudentMapper(1L, "John", "Smith");
		StudentEntity result = StudentController.getEntity(studentMapper);
		assertEquals(1L, (long) result.getId());
		assertEquals("John", result.getFirstName());
		assertEquals("Smith", result.getLastName());
	}

	@Test
	public void testGetEntityNegative() {
		StudentEntity result = StudentController.getEntity(null);
		assertEquals(null, result);
	}

}
