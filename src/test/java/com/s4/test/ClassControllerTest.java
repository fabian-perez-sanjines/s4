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

public class ClassControllerTest {
	private ClassController controller;
	private ClassService classService;

	@Before
	public void setup() {
		classService = mock(ClassService.class);
		controller = new ClassController();
		controller.setClassController(classService);
	}

	@Test
	public void testClassGetAll() {
		List<ClassMapper> all = new LinkedList<ClassMapper>();
		all.add(new ClassMapper(1L, "Algorithms", "Algorithms Description"));
		all.add(new ClassMapper(2L, "Compilers", "Compilers Description"));

		when(classService.get(new HashMap<String, String>())).thenReturn(all);
		Response response = controller.get(null, null);
		verify(classService).get(new HashMap<String, String>());
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

		when(classService.get(params)).thenReturn(all);
		Response response = controller.get("Algorithms", null);
		verify(classService).get(params);
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

		when(classService.get(params)).thenReturn(all);
		Response response = controller.get(null, "Algorithms Description");
		verify(classService).get(params);
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

		when(classService.get(params)).thenReturn(all);
		Response response = controller.get("software generation", null);
		verify(classService).get(params);
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

		when(classService.get(params)).thenReturn(all);
		Response response = controller.get(null, "software generation");
		verify(classService).get(params);
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

		when(classService.get(params)).thenReturn(all);
		Response response = controller.get("Algorithms", "Algorithms Description");
		verify(classService).get(params);
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

		when(classService.get(params)).thenReturn(all);
		Response response = controller.get("Algorithms", "Algorithms Description");
		verify(classService).get(params);
		assertEquals(response.getStatus(), 200);
		@SuppressWarnings("unchecked")
		List<ClassMapper> list = (List<ClassMapper>) response.getEntity();
		assertEquals(0, list.size());
	}

	@Test
	public void testStudentGetByCodeNotFound() {
		ClassMapper studentResponse = null;
		when(classService.get(5L)).thenReturn(studentResponse);
		Response response = controller.getByID(5L);
		verify(classService).get(5L);
		assertEquals(response.getStatus(), 404);
	}

	@Test
	public void testStudentGetByCodePsitive() {
		ClassMapper studentResponse = new ClassMapper(1L, "Algorithms", "Algorithms Description");
		when(classService.get(1L)).thenReturn(studentResponse);
		Response response = controller.getByID(1L);
		verify(classService).get(1L);
		assertEquals(response.getStatus(), 200);
		assertEquals(response.getEntity(), studentResponse);
	}

}
