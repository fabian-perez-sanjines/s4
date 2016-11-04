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

public class StudentControllerTest {
	private StudentController controller;
	private StudentService studentService;

	@Before
	public void setup() {
		studentService = mock(StudentService.class);
		controller = new StudentController();
		controller.setStudentController(studentService);
	}

	@Test
	public void testStudentGetAll() {
		List<StudentMapper> all = new LinkedList<StudentMapper>();
		all.add(new StudentMapper(1L, "joe", "Doe"));
		all.add(new StudentMapper(2L, "Jack", "Smith"));

		when(studentService.get(new HashMap<String, String>())).thenReturn(all);
		Response response = controller.get(null, null);
		verify(studentService).get(new HashMap<String, String>());
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

		when(studentService.get(params)).thenReturn(all);
		Response response = controller.get("joe", null);
		verify(studentService).get(params);
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

		when(studentService.get(params)).thenReturn(all);
		Response response = controller.get(null, "Doe");
		verify(studentService).get(params);
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

		when(studentService.get(params)).thenReturn(all);
		Response response = controller.get("jack", null);
		verify(studentService).get(params);
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

		when(studentService.get(params)).thenReturn(all);
		Response response = controller.get(null, "terrence");
		verify(studentService).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<StudentMapper> list = (List<StudentMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetByIDNotFound() {
		StudentMapper studentResponse = null;
		when(studentService.get(5L)).thenReturn(studentResponse);
		Response response = controller.getByID(5L);
		verify(studentService).get(5L);
		assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testStudentGetByIDPsitive() {
		StudentMapper studentResponse = new StudentMapper(1L, "Joe", "Smith");
		when(studentService.get(1L)).thenReturn(studentResponse);
		Response response = controller.getByID(1L);
		verify(studentService).get(1L);
		assertEquals(response.getStatus(), 200);
		assertEquals(response.getEntity(), studentResponse);
	}

}
