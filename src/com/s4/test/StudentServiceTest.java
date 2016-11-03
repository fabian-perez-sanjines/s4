package com.s4.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.s4.controller.StudentController;
import com.s4.model.StudentMapper;
import com.s4.service.StudentService;

public class StudentServiceTest {
	private StudentService service;
	private StudentController student;
	
	@Before
	public void setup(){
		student = mock(StudentController.class);
		service = new StudentService();
		service.setStudentController(student);
	}
	
	@Test
	public void testStudentGetAll(){
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		all.add(new StudentMapper(1L, "Pedro", "Lopez"));
		all.add(new StudentMapper(2L, "Juan", "Romero"));
		when(student.get()).thenReturn(all);
		Response response = service.get();
		verify(student).get();
		assertEquals(response.getStatus(), 200);
	}
	
	
	@Test
	public void testStudentGetByIDNotFound(){
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		when(student.get()).thenReturn(all);
		Response response = service.get();
		verify(student).get();
		assertEquals(response.getStatus(), 200);
	}

}
