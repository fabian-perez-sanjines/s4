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
 * Manager In charge of manage classes in the datastore, this class uses
 * objectify to save, load, update and delete classes.
 * 
 * @author Fabian Perez
 * 
 */
public class ClassManager {

	private static final Logger log = Logger.getLogger(ClassManager.class.getName());

	static {
		ObjectifyService.register(ClassEntity.class);
	}

	/**
	 * Method that load the class that match the given code from the datastore.
	 * 
	 * @param code
	 *            the code that will be used to search the class.
	 * @return class entity that match the given code.
	 */
	public ClassEntity get(Long code) {
		log.info(MessageFormat.format("The method get by code was called for code ={0}", code));
		ClassEntity classEntity = ofy().load().type(ClassEntity.class).id(code).now();
		return classEntity;
	}

	/**
	 * Method that load the classes that match the filter parameter from the
	 * datastore.
	 * 
	 * @param queryParams
	 *            map that contains the filter parameter, the key represent the
	 *            field and the value the value to filter.
	 * @return List of classes entities that match the filter parameter.
	 */
	public List<ClassEntity> get(Map<String, String> queryParams) {
		log.info("The method get by filters was called");
		Query<ClassEntity> query = ofy().load().type(ClassEntity.class);
		for (String key : queryParams.keySet()) {
			query = query.filter(key, queryParams.get(key));
		}
		List<ClassEntity> classEntity = query.list();

		return classEntity;
	}

	/**
	 * Method to save the given class in the datastore.
	 * 
	 * @param classEntity
	 *            the class to be created.
	 * @return Entity of the created class with the generated code.
	 */
	public ClassEntity store(ClassEntity classEntity) {
		log.info(MessageFormat.format("The method store was called for title = {0}", classEntity.getTitle()));
		ofy().save().entity(classEntity).now();
		return classEntity;
	}

	/**
	 * Method that update the given class in the datastore.
	 * 
	 * @param classEntity
	 *            the class to be updated.
	 * @return entity of the updated class.
	 */
	public ClassEntity update(ClassEntity classEntity) {
		log.info(MessageFormat.format("The method update  was called for code = {0}", classEntity.getCode()));
		ClassEntity storedClass = get(classEntity.getCode());
		if (storedClass == null)
			return null;
		Result<Key<ClassEntity>> result = ofy().save().entity(classEntity);
		result.now();
		return classEntity;
	}

	/**
	 * Method that delete a class given a code from the datastore.
	 * 
	 * @param code
	 *            the code that will be used to delete the class.
	 * @return true if the deletion was correct and false if not.
	 */
	public boolean delete(Long code) {
		log.info(MessageFormat.format("The method delete was called for code = {0}", code));
		ClassEntity classEntity = get(code);
		if (classEntity == null)
			return false;
		try {
			ofy().delete().type(ClassEntity.class).id(code);
		} catch (Exception exception) {
			log.warning(MessageFormat.format("Error trying to delete a class with code = {0}", code));
			return false;
		}
		return true;
	}

	/**
	 * Method that load the classes that match the list of keys from the
	 * datastore.
	 * 
	 * @param keys
	 *            list of keys to obtain the classes
	 * @return List of classes entities that match the list of keys.
	 */
	public List<ClassEntity> getByIds(List<Key<ClassEntity>> keys) {
		return new ArrayList<ClassEntity>(ofy().load().keys(keys).values());
	}

	/**
	 * Method that obtains all registered students in the class with the given
	 * code from the datastore.
	 * 
	 * @param code
	 *            the class code that will be used to obtain the class students.
	 * @return List of students that are registered in the class with the given
	 *         code.
	 */
	public List<StudentEntity> getStudents(Long code) {
		log.info(MessageFormat.format("The method get students was called for code = {0}", code));
		ClassEntity classEntity = get(code);
		if (classEntity == null)
			return null;
		StudentManager studentManager = new StudentManager();
		return studentManager.getByIds(classEntity.getStudents());
	}

	/**
	 * Method that register a student with the given id in a class with the
	 * given code.
	 * 
	 * @param code
	 *            to identify the class where the student will be registered.
	 * @param id
	 *            to identify the student to be registered.
	 * 
	 * @return the list of classes with their respective generated ids, null if
	 *         there is a problem.
	 */
	public Key<ClassEntity> register(Long code, Key<StudentEntity> id) {
		log.info(MessageFormat.format("The method register was called for code = {0}", code));
		ClassEntity classEntity = get(code);
		if (classEntity != null) {
			try {
				List<Key<StudentEntity>> studentsKeys = classEntity.getStudents();
				if (studentsKeys == null)
					studentsKeys = new ArrayList<>();
				if (!studentsKeys.contains(id)) {
					studentsKeys.add(id);
					classEntity.setStudents(studentsKeys);
					update(classEntity);
					return Key.create(ClassEntity.class, code);
				}

			} catch (Exception exception) {
				log.warning(MessageFormat.format(
						"Error trying to register a student with id = {0} in the class with code = {1}", id, code));
			}
		}
		return null;
	}
}
