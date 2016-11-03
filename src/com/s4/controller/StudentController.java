package com.s4.controller;

import java.util.ArrayList;
import java.util.List;
import com.s4.data.access.layer.StudentManager;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;
import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;

public class StudentController {

	private StudentManager studentManager;
	public StudentController()
	{
		studentManager = new StudentManager();	
	}
	
	public List<StudentMapper> get()
	{
		List<StudentMapper> studentsMapper = new ArrayList<>();
		
		List<StudentEntity> students = studentManager.get();
		for(StudentEntity student: students)
		{
			studentsMapper.add(getMapper(student));
		}
		
		return studentsMapper;
	}

	public StudentMapper get(Long id)
	{
		StudentEntity student = studentManager.get(id);
		return getMapper(student);
	}
	
	public StudentMapper update(Long id, StudentMapper student) {
		student.setId(id);
		StudentEntity storedStudent = studentManager.update(getEntity(student));
		StudentMapper studentResponse = getMapper(storedStudent);
		return studentResponse;
	}

	public boolean delete(Long id) {
		return studentManager.delete(id);
	}
	
	public boolean registerClass(Long id, Long code) {
		return studentManager.register(id, code);
	}
	
	public StudentMapper create(StudentMapper studentsMapper)
	{	
		StudentEntity storedStudent = studentManager.store(getEntity(studentsMapper));
		StudentMapper studentResponse = getMapper(storedStudent);
		return studentResponse;
	}
	
	public List<ClassMapper> getClasses(Long id) {
		List<ClassMapper> classMapper = new ArrayList<ClassMapper>();
		List<ClassEntity> classes = studentManager.getClasses(id);
		if(classes == null)
			return null;
		for(ClassEntity classEntity: classes)
		{
			classMapper.add(ClassController.getMapper(classEntity));
		}
		
		return classMapper;
	}
	
	public static StudentMapper getMapper(StudentEntity student) {
		if(student == null)
			return null;
		StudentMapper studentsMapper = new StudentMapper();
		studentsMapper.setId(student.getId());
		studentsMapper.setFirstName(student.getFirstName());
		studentsMapper.setLastName(student.getLastName());
		return studentsMapper;
	}
	
	public static StudentEntity getEntity(StudentMapper studentsMapper) {
		if(studentsMapper == null)
			return null;
		StudentEntity student = new StudentEntity();
		student.setId(studentsMapper.getId());
		student.setFirstName(studentsMapper.getFirstName());
		student.setLastName(studentsMapper.getLastName());
		return student;
	}
}
