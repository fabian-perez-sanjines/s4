package com.s4.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.s4.entity.StudentEntity;
import com.s4.model.StudentMapper;
import com.s4.service.StudentService;

public class StudentServiceTest {

	@Test
	public void testGetMapperPositive() {
		StudentEntity studentEntity = new StudentEntity(1L, "John", "Smith");
		StudentMapper result = StudentService.getMapper(studentEntity);
		assertEquals(1L, (long) result.getId());
		assertEquals("John", result.getFirstName());
		assertEquals("Smith", result.getLastName());
	}

	@Test
	public void testGetMapperNegative() {
		StudentMapper result = StudentService.getMapper(null);
		assertEquals(null, result);
	}

	@Test
	public void testGetEntityPositive() {
		StudentMapper studentMapper = new StudentMapper(1L, "John", "Smith");
		StudentEntity result = StudentService.getEntity(studentMapper);
		assertEquals(1L, (long) result.getId());
		assertEquals("John", result.getFirstName());
		assertEquals("Smith", result.getLastName());
	}

	@Test
	public void testGetEntityNegative() {
		StudentEntity result = StudentService.getEntity(null);
		assertEquals(null, result);
	}

}
