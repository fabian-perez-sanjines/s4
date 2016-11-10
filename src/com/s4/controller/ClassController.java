package com.s4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.s4.data.access.layer.ClassManager;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;
import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;

/**
 * Controller In charge of Manage classes, this class contains CRUD methods and
 * has relation whit the datastore managers and the services.
 * 
 * @author Fabian Perez
 * 
 */
public class ClassController {

	private ClassManager classManager;

	/**
	 * Constructor that instantiate classManager.
	 */
	public ClassController() {
		classManager = new ClassManager();
	}

	/**
	 * Method that calls the class manager to obtain all the classes that meet
	 * the filter parameter.
	 * 
	 * @param params
	 *            map that contains the filter parameter, the key represent the
	 *            field and the value the value to filter.
	 * @return List of classes POJO that meet the filter parameter.
	 */
	public List<ClassMapper> get(Map<String, String> params) {
		List<ClassMapper> classesMapper = new ArrayList<>();

		List<ClassEntity> classes = classManager.get(params);
		for (ClassEntity classEntity : classes) {
			classesMapper.add(getMapper(classEntity));
		}

		return classesMapper;
	}

	/**
	 * Method that calls the class manager to obtain the class that match the
	 * given code.
	 * 
	 * @param code
	 *            the code that will be used to search the class.
	 * @return POJO of the class that match the given code.
	 */
	public ClassMapper get(Long code) {
		ClassEntity classEntity = classManager.get(code);
		return getMapper(classEntity);
	}

	/**
	 * Method that calls the class manager to update the given class that match
	 * the given code.
	 * 
	 * @param code
	 *            the code that will be used to search the class.
	 * @param classMapper
	 *            the class to be updated.
	 * @return POJO of the updated class.
	 */
	public ClassMapper update(Long code, ClassMapper classMapper) {
		classMapper.setCode(code);
		ClassEntity storedClass = classManager.update(getEntity(classMapper));
		ClassMapper classResponse = getMapper(storedClass);
		return classResponse;
	}

	/**
	 * Method that calls the class manager to delete a class given a code.
	 * 
	 * @param code
	 *            the code that will be used to delete the class.
	 * @return true if the deletion was correct and false if not.
	 */
	public boolean delete(Long code) {
		return classManager.delete(code);
	}

	/**
	 * Method that calls the class manager to create the given class.
	 * 
	 * @param classMapper
	 *            the class to be created.
	 * @return POJO of the created class with the generated code.
	 */
	public ClassMapper create(ClassMapper classMapper) {
		ClassEntity storedClass = classManager.store(getEntity(classMapper));
		ClassMapper classResponse = getMapper(storedClass);
		return classResponse;
	}

	/**
	 * Method that calls the class manager to obtain all registered students in
	 * the class with the given code.
	 * 
	 * @param code
	 *            the class code that will be used to obtain the class students.
	 * @return List of students POJO that are registered in the class with the
	 *         given code.
	 */
	public List<StudentMapper> getStudents(Long code) {
		List<StudentMapper> studentsMapper = new ArrayList<>();
		List<StudentEntity> students = classManager.getStudents(code);
		if (students == null)
			return null;
		for (StudentEntity student : students) {
			studentsMapper.add(StudentController.getMapper(student));
		}

		return studentsMapper;
	}

	/**
	 * Given a entity class returns a class POJO.
	 * 
	 * @param classEntity
	 *            class entity to be converted.
	 * @return class POJO.
	 */
	public static ClassMapper getMapper(ClassEntity classEntity) {
		if (classEntity == null)
			return null;
		ClassMapper classMapper = new ClassMapper(classEntity.getCode(), classEntity.getTitle(),
				classEntity.getDescription());
		return classMapper;
	}

	/**
	 * Given a class POJO returns a class entity.
	 * 
	 * @param classMapper
	 *            class POJO to be converted.
	 * @return class entity.
	 */
	public static ClassEntity getEntity(ClassMapper classMapper) {
		if (classMapper == null)
			return null;
		ClassEntity classEntity = new ClassEntity(classMapper.getCode(), classMapper.getTitle(),
				classMapper.getDescription());
		return classEntity;
	}
}