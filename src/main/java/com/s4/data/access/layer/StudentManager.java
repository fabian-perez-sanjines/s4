package com.s4.data.access.layer;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;

/**
 * Manager In charge of manage students in the datastore, this class uses
 * objectify to save, load, update and delete classes.
 * 
 * @author Fabian Perez
 * 
 */
public class StudentManager {

	private static final Logger log = Logger.getLogger(StudentManager.class.getName());

	static {
		ObjectifyService.register(StudentEntity.class);
	}

	/**
	 * Method that load the student that match the given id from the datastore.
	 * 
	 * @param id
	 *            the id that will be used to search the student.
	 * @return student entity that match the given id.
	 */
	public StudentEntity get(Long id) {
		log.info(MessageFormat.format("The method get by id was called for id ={0}", id));
		StudentEntity student = ofy().load().type(StudentEntity.class).id(id).now();
		return student;
	}

	/**
	 * Method that load the students that match the filter parameter from the
	 * datastore.
	 * 
	 * @param queryParams
	 *            map that contains the filter parameter, the key represent the
	 *            field and the value the value to filter.
	 * @return List of students entities that match the filter parameter.
	 */
	public List<StudentEntity> get(Map<String, String> queryParams) {
		log.info("The method get by filters was called");
		Query<StudentEntity> query = ofy().load().type(StudentEntity.class);
		for (String key : queryParams.keySet()) {
			query = query.filter(key, queryParams.get(key));
		}
		List<StudentEntity> students = query.list();

		return students;
	}

	/**
	 * Method to save the given student in the datastore.
	 * 
	 * @param student
	 *            the student to be created.
	 * @return Entity of the created student with the generated id.
	 */
	public StudentEntity store(StudentEntity student) {
		log.info(MessageFormat.format("The method store was called for firstName = {0}", student.getFirstName()));
		Result<Key<StudentEntity>> result = ofy().save().entity(student);
		result.now();
		return student;
	}

	/**
	 * Method that update the given student in the datastore.
	 * 
	 * @param student
	 *            the student to be updated.
	 * @return entity of the updated student.
	 */
	public StudentEntity update(StudentEntity student) {
		log.info(MessageFormat.format("The method update  was called for id = {0}", student.getId()));
		StudentEntity storedStudent = get(student.getId());
		if (storedStudent == null)
			return null;
		Result<Key<StudentEntity>> result = ofy().save().entity(student);
		result.now();
		return student;
	}

	/**
	 * Method that delete a student given a id from the datastore.
	 * 
	 * @param id
	 *            the id that will be used to delete the student.
	 * @return true if the deletion was correct and false if not.
	 */
	public boolean delete(Long id) {
		log.info(MessageFormat.format("The method delete was called for id = {0}", id));
		StudentEntity student = get(id);
		if (student == null)
			return false;
		try {
			ofy().delete().type(StudentEntity.class).id(id);
		} catch (Exception exception) {
			log.warning(MessageFormat.format("Error trying to delete a student with id = {0}", student));
			return false;
		}
		return true;
	}

	/**
	 * Method that load the students that match the list of keys from the
	 * datastore.
	 * 
	 * @param keys
	 *            list of keys to obtain the students
	 * @return List of student entities that match the list of keys.
	 */
	public List<StudentEntity> getByIds(List<Key<StudentEntity>> keys) {
		return new ArrayList<StudentEntity>(ofy().load().keys(keys).values());
	}

	/**
	 * Method that obtains all classes where the student with the given id is
	 * registered from the datastore.
	 * 
	 * @param id
	 *            the student id that will be used to obtain the student
	 *            classes.
	 * @return List of classes where the the student with the given id is
	 *         registered.
	 */
	public List<ClassEntity> getClasses(Long id) {
		log.info(MessageFormat.format("The method get classes was called for id = {0}", id));
		StudentEntity studentEntity = get(id);
		if (studentEntity == null)
			return null;
		ClassManager classManager = new ClassManager();
		return classManager.getByIds(studentEntity.getClasses());
	}

	/**
	 * Method that register a student with the given id in a class with the
	 * given code.
	 * 
	 * @param id
	 *            to identify the student to be registered.
	 * 
	 * @param code
	 *            to identify the class where the student will be registered.
	 * 
	 * 
	 * @return true if the student was registered, false if there is a problem.
	 */
	public boolean register(Long id, Long code) {
		log.info(MessageFormat.format("The method register was called for code = {0} and id = {1}", code, id));
		StudentEntity student = get(id);
		if (student != null) {
			ClassManager classManager = new ClassManager();
			Key<StudentEntity> studentKey = Key.create(StudentEntity.class, id);
			Key<ClassEntity> classKey = classManager.register(code, studentKey);
			if (classKey != null)
				return register(student, classKey);
		}
		return false;
	}

	/**
	 * Method that register a given student in a class with the given key.
	 * 
	 * @param studentEntity
	 *            student to be registered.
	 * @param classKey
	 *            to identify the class where the student will be registered.
	 * 
	 * @return true if the student was registered, false if there is a problem.
	 */
	private boolean register(StudentEntity studentEntity, Key<ClassEntity> classKey) {
		log.info("The method register was called");
		try {
			List<Key<ClassEntity>> classKeys = studentEntity.getClasses();
			if (classKeys == null)
				classKeys = new ArrayList<>();
			if (!classKeys.contains(classKey)) {
				classKeys.add(classKey);
				studentEntity.setClasses(classKeys);
				update(studentEntity);
				return true;
			}

		} catch (Exception exception) {
			log.warning(MessageFormat.format("Error trying to register a student with id = {0} in the class",
					studentEntity.getId()));
		}

		return false;
	}

}
