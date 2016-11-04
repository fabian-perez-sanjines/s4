package com.s4.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.s4.controller.ClassController;
import com.s4.entity.ClassEntity;
import com.s4.model.ClassMapper;

public class ClassControllerTest {


	@Test
	public void testGetMapperPositive() {
		ClassEntity classEntity = new ClassEntity(1L, "Algorithms", "Algorithms Description");
		ClassMapper result = ClassController.getMapper(classEntity);
		assertEquals(1L, (long)result.getCode());
		assertEquals("Algorithms", result.getTitle());
		assertEquals("Algorithms Description", result.getDescription());
	}
	
	@Test
	public void testGetMapperNegative() {
		ClassMapper result = ClassController.getMapper(null);
		assertEquals(null, result);
	}
	
	@Test
	public void testGetEntityPositive() {
		ClassMapper classMapper = new ClassMapper(1L, "Algorithms", "Algorithms Description");
		ClassEntity result = ClassController.getEntity(classMapper);
		assertEquals(1L, (long)result.getCode());
		assertEquals("Algorithms", result.getTitle());
		assertEquals("Algorithms Description", result.getDescription());
	}
	
	@Test
	public void testGetEntityNegative() {
		ClassEntity result = ClassController.getEntity(null);
		assertEquals(null, result);
	}

}
