package com.s4.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.s4.entity.ClassEntity;
import com.s4.model.ClassMapper;
import com.s4.service.ClassService;

public class ClassServiceTest {


	@Test
	public void testGetMapperPositive() {
		ClassEntity classEntity = new ClassEntity(1L, "Algorithms", "Algorithms Description");
		ClassMapper result = ClassService.getMapper(classEntity);
		assertEquals(1L, (long)result.getCode());
		assertEquals("Algorithms", result.getTitle());
		assertEquals("Algorithms Description", result.getDescription());
	}
	
	@Test
	public void testGetMapperNegative() {
		ClassMapper result = ClassService.getMapper(null);
		assertEquals(null, result);
	}
	
	@Test
	public void testGetEntityPositive() {
		ClassMapper classMapper = new ClassMapper(1L, "Algorithms", "Algorithms Description");
		ClassEntity result = ClassService.getEntity(classMapper);
		assertEquals(1L, (long)result.getCode());
		assertEquals("Algorithms", result.getTitle());
		assertEquals("Algorithms Description", result.getDescription());
	}
	
	@Test
	public void testGetEntityNegative() {
		ClassEntity result = ClassService.getEntity(null);
		assertEquals(null, result);
	}

}
