package com.s4.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.s4.data.access.layer.StudentManager;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;
import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;

/**
 * Controller In charge of Manage students, this class contains CRUD methods and
 * has relation whit the datastore managers and the services.
 * 
 * @author Fabian Perez
 * 
 */
public class StudentController {

	private StudentManager studentManager;

	/**
	 * Constructor that instantiate studentManager.
	 */
	public StudentController() {
		studentManager = new StudentManager();
	}

	/**
	 * Method that calls the student manager to obtain all the students that
	 * meet the filter parameter.
	 * 
	 * @param params
	 *            map that contains the filter parameter, the key represent the
	 *            field and the value the value to filter.
	 * @return List of students POJO that meet the filter parameter.
	 */
	public List<StudentMapper> get(Map<String, String> params) {
		List<StudentMapper> studentsMapper = new ArrayList<>();

		List<StudentEntity> students = studentManager.get(params);
		for (StudentEntity student : students) {
			studentsMapper.add(getMapper(student));
		}

		return studentsMapper;
	}

	/**
	 * Method that calls the student manager to obtain the student that match
	 * the given id.
	 * 
	 * @param id
	 *            the id that will be used to search the class.
	 * @return POJO of the student that match the given id.
	 */
	public StudentMapper get(Long id) {
		StudentEntity student = studentManager.get(id);
		return getMapper(student);
	}

	/**
	 * Method that calls the student manager to update the given student that
	 * match the given id.
	 * 
	 * @param id
	 *            the id that will be used to search the student.
	 * @param student
	 *            the student to be updated.
	 * @return POJO of the updated student.
	 */
	public StudentMapper update(Long id, StudentMapper student) {
		student.setId(id);
		StudentEntity storedStudent = studentManager.update(getEntity(student));
		StudentMapper studentResponse = getMapper(storedStudent);
		return studentResponse;
	}

	/**
	 * Method that calls the student manager to delete a student given a id.
	 * 
	 * @param id
	 *            the id that will be used to delete the student.
	 * @return true if the deletion was correct and false if not.
	 */
	public boolean delete(Long id) {
		return studentManager.delete(id);
	}

	/**
	 * Method that calls the student manager to register a user with the given
	 * id in a class with the given code.
	 * 
	 * @param id
	 *            to identify the student to be registered.
	 * @param code
	 *            to identify the class where the student will be registered.
	 * @return true if the user could be registered
	 */
	public boolean registerClass(Long id, Long code) {
		return studentManager.register(id, code);
	}

	/**
	 * Method that calls the student manager to create the given student.
	 * 
	 * @param studentMapper
	 *            the student to be created.
	 * @return POJO of the created student with the generated id.
	 */
	public StudentMapper create(StudentMapper studentMapper) {
		StudentEntity storedStudent = studentManager.store(getEntity(studentMapper));
		StudentMapper studentResponse = getMapper(storedStudent);
		return studentResponse;
	}

	/**
	 * Method that calls the student manager to obtain all classes that the
	 * student with the id is registered.
	 * 
	 * @param id
	 *            the id that will be used to obtain the student classes.
	 * @return List of classes POJO where the student with the given id is
	 *         registered.
	 */
	public List<ClassMapper> getClasses(Long id) {
		List<ClassMapper> classMapper = new ArrayList<ClassMapper>();
		List<ClassEntity> classes = studentManager.getClasses(id);
		if (classes == null)
			return null;
		for (ClassEntity classEntity : classes) {
			classMapper.add(ClassController.getMapper(classEntity));
		}

		return classMapper;
	}

	/**
	 * Given a student entity returns a student POJO.
	 * 
	 * @param student
	 *            student entity to be converted.
	 * @return student POJO.
	 */
	public static StudentMapper getMapper(StudentEntity student) {
		if (student == null)
			return null;
		StudentMapper studentsMapper = new StudentMapper();
		studentsMapper.setId(student.getId());
		studentsMapper.setFirstName(student.getFirstName());
		studentsMapper.setLastName(student.getLastName());
		return studentsMapper;
	}

	/**
	 * Given a student POJO returns a student entity.
	 * 
	 * @param studentsMapper
	 *            student POJO to be converted.
	 * @return student entity.
	 */
	public static StudentEntity getEntity(StudentMapper studentsMapper) {
		if (studentsMapper == null)
			return null;
		StudentEntity student = new StudentEntity();
		student.setId(studentsMapper.getId());
		student.setFirstName(studentsMapper.getFirstName());
		student.setLastName(studentsMapper.getLastName());
		return student;
	}
}
