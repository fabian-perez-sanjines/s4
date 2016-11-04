package com.s4.test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.s4.controller.StudentController;
import com.s4.model.StudentMapper;
import com.s4.service.StudentService;

public class StudentServiceTest {
	private StudentService service;
	private StudentController studentController;

	@Before
	public void setup() {
		studentController = mock(StudentController.class);
		service = new StudentService();
		service.setStudentController(studentController);
	}

	@Test
	public void testStudentGetAll() {
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		all.add(new StudentMapper(1L, "joe", "Doe"));
		all.add(new StudentMapper(2L, "Jack", "Smith"));

		when(studentController.get(new HashMap<String, String>())).thenReturn(all);
		Response response = service.get(null, null);
		verify(studentController).get(new HashMap<String, String>());
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<StudentMapper> list = (List<StudentMapper>) response.getEntity();
		assertEquals(2, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterFirstNamePositive() {
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		all.add(new StudentMapper(1L, "joe", "Doe"));
		Map<String, String> params = new HashMap<String, String>();
		params.put("firstname", "joe");

		when(studentController.get(params)).thenReturn(all);
		Response response = service.get("joe", null);
		verify(studentController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<StudentMapper> list = (List<StudentMapper>) response.getEntity();
		assertEquals(1, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterLastNamePositive() {
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		all.add(new StudentMapper(1L, "joe", "Doe"));
		Map<String, String> params = new HashMap<String, String>();
		params.put("lastname", "Doe");

		when(studentController.get(params)).thenReturn(all);
		Response response = service.get(null, "Doe");
		verify(studentController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<StudentMapper> list = (List<StudentMapper>) response.getEntity();
		assertEquals(1, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterFirstNameNegative() {
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("firstname", "jack");

		when(studentController.get(params)).thenReturn(all);
		Response response = service.get("jack", null);
		verify(studentController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<StudentMapper> list = (List<StudentMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterLastNameNegative() {
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("lastname", "terrence");

		when(studentController.get(params)).thenReturn(all);
		Response response = service.get(null, "terrence");
		verify(studentController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<StudentMapper> list = (List<StudentMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetByIDNotFound() {
		StudentMapper studentResponse = null;
		when(studentController.get(5L)).thenReturn(studentResponse);
		Response response = service.getByID(5L);
		verify(studentController).get(5L);
		assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testStudentGetByIDPsitive() {
		StudentMapper studentResponse = new StudentMapper(1L, "Joe", "Smith");
		when(studentController.get(1L)).thenReturn(studentResponse);
		Response response = service.getByID(1L);
		verify(studentController).get(1L);
		assertEquals(response.getStatus(), 200);
		assertEquals(response.getEntity(), studentResponse);
	}

}
