package com.s4.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.s4.controller.ClassController;
import com.s4.model.ClassMapper;
import com.s4.service.ClassService;

public class ClassServiceTest {
	private ClassService service;
	private ClassController classController;

	@Before
	public void setup() {
		classController = mock(ClassController.class);
		service = new ClassService();
		service.setClassController(classController);
	}

	@Test
	public void testClassGetAll() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		all.add(new ClassMapper(1L, "Algorithms", "Algorithms Description"));
		all.add(new ClassMapper(2L, "Compilers", "Compilers Description"));

		when(classController.get(new HashMap<String, String>())).thenReturn(all);
		Response response = service.get(null, null);
		verify(classController).get(new HashMap<String, String>());
		assertEquals(200, response.getStatus());
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(2, list.size());
	}

	@Test
	public void testClassGetAllWithFilterTitlePositive() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		all.add(new ClassMapper(1L, "Algorithms", "Algorithms Description"));
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", "Algorithms");

		when(classController.get(params)).thenReturn(all);
		Response response = service.get("Algorithms", null);
		verify(classController).get(params);
		assertEquals(200, response.getStatus());
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(1, list.size());
	}

	@Test
	public void testClassGetAllWithFilterDescriptionPositive() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		all.add(new ClassMapper(1L, "Algorithms", "Algorithms Description"));
		Map<String, String> params = new HashMap<String, String>();
		params.put("description", "Algorithms Description");

		when(classController.get(params)).thenReturn(all);
		Response response = service.get(null, "Algorithms Description");
		verify(classController).get(params);
		assertEquals(200, response.getStatus());
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(1, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterFirstNameNegative() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", "software generation");

		when(classController.get(params)).thenReturn(all);
		Response response = service.get("software generation", null);
		verify(classController).get(params);
		assertEquals(200, response.getStatus());
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterLastNameNegative() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("description", "software generation");

		when(classController.get(params)).thenReturn(all);
		Response response = service.get(null, "software generation");
		verify(classController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterFirstNameAndLastNamePositive() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		all.add(new ClassMapper(1L, "Algorithms", "Algorithms Description"));
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", "Algorithms");
		params.put("description", "Algorithms Description");

		when(classController.get(params)).thenReturn(all);
		Response response = service.get("Algorithms", "Algorithms Description");
		verify(classController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(1, list.size());
	}

	@Test
	public void testStudentGetAllWithFilterFirstNameAndLastNameNegative() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", "Algorithms");
		params.put("description", "Algorithms Description");

		when(classController.get(params)).thenReturn(all);
		Response response = service.get("Algorithms", "Algorithms Description");
		verify(classController).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetByCodeNotFound() {
		ClassMapper studentResponse = null;
		when(classController.get(5L)).thenReturn(studentResponse);
		Response response = service.getByID(5L);
		verify(classController).get(5L);
		assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testStudentGetByCodePsitive() {
		ClassMapper studentResponse = new ClassMapper(1L, "Algorithms", "Algorithms Description");
		when(classController.get(1L)).thenReturn(studentResponse);
		Response response = service.getByID(1L);
		verify(classController).get(1L);
		assertEquals(response.getStatus(), 200);
		assertEquals(response.getEntity(), studentResponse);
	}

}
