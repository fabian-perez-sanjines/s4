package com.s4.controller;

import java.util.ArrayList;
import java.util.List;
import com.s4.data.access.layer.ClassManager;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;
import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;

public class ClassController {

	private ClassManager classManager;
	public ClassController()
	{
		classManager = new ClassManager();	
	}
	
	public List<ClassMapper> get()
	{
		List<ClassMapper> classesMapper = new ArrayList<>();
		
		List<ClassEntity> classes = classManager.get();
		for(ClassEntity classEntity: classes)
		{
			classesMapper.add(getMapper(classEntity));
		}
		
		return classesMapper;
	}

	public ClassMapper get(Long code)
	{
		ClassEntity classEntity = classManager.get(code);
		return getMapper(classEntity);
	}
	
	public ClassMapper update(Long code, ClassMapper classMapper) {
		classMapper.setCode(code);
		ClassEntity storedClass = classManager.update(getEntity(classMapper));
		ClassMapper classResponse = getMapper(storedClass);
		return classResponse;
	}

	public boolean delete(Long code) {
		return classManager.delete(code);
	}
	
	public ClassMapper create(ClassMapper ClassMapper)
	{	
		ClassEntity storedClass = classManager.store(getEntity(ClassMapper));
		ClassMapper classResponse = getMapper(storedClass);
		return classResponse;
	}
	
	public List<StudentMapper> getStudents(Long code) {
		List<StudentMapper> studentsMapper = new ArrayList<>();
		List<StudentEntity> students = classManager.getStudents(code);
		if(students == null)
			return null;
		for(StudentEntity student: students)
		{
			studentsMapper.add(StudentController.getMapper(student));
		}
		
		return studentsMapper;
	}

	public static ClassMapper getMapper(ClassEntity classEntity) {
		if(classEntity == null)
			return null;
		ClassMapper classMapper = new ClassMapper();
		classMapper.setCode(classEntity.getCode());
		classMapper.setTitle(classEntity.getTitle());
		classMapper.setDescription(classEntity.getDescription());
		return classMapper;
	}
	
	public static ClassEntity getEntity(ClassMapper classMapper) {
		if(classMapper == null)
			return null;
		ClassEntity classEntity = new ClassEntity();
		classEntity.setCode(classMapper.getCode());
		classEntity.setTitle(classMapper.getTitle());
		classEntity.setDescription(classMapper.getDescription());
		return classEntity;
	}
}