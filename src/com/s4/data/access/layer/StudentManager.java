package com.s4.data.access.layer;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class StudentManager 
{
	static {
		ObjectifyService.register(StudentEntity.class);
    }
	
	public StudentManager(){	
	}

	public StudentEntity get(Long id){
		StudentEntity student = ofy().load().type(StudentEntity.class).id(id).now();
		return student;
	}
	
	
	public List<StudentEntity> get(){
		List<StudentEntity> students = ofy().load().type(StudentEntity.class).list();
		
		return students;
	}
	
	public StudentEntity store(StudentEntity student){
		Result<Key<StudentEntity>> result = ofy().save().entity(student);
		result.now();
		return student;
	}
	
	public StudentEntity update(StudentEntity student){
		StudentEntity storedStudent = get(student.getId());
		if(storedStudent == null)
			return null;
		Result<Key<StudentEntity>> result = ofy().save().entity(student);
		result.now();
		return student;
	}

	public boolean delete(Long id) {
		
		StudentEntity student = get(id);
		if(student == null)
			return false;
		try{
			ofy().delete().type(StudentEntity.class).id(id);
		}catch(Exception exception){
			return false;
		}
		return true;
	}

	public List<StudentEntity> getByIds(List<Key<StudentEntity>> keys) {
		return new ArrayList<StudentEntity>(ofy().load().keys(keys).values()); 
	}
	
	public List<ClassEntity> getClasses(Long id) {
		StudentEntity studentEntity = get(id);
		if(studentEntity == null)
			return null;
		ClassManager classManager = new ClassManager();
		return classManager.getByIds(studentEntity.getClasses());
	}

	public boolean register(Long id, Long code) {
		StudentEntity student = get(id);
		if(student != null)
		{
			ClassManager classManager = new ClassManager();
			Key<StudentEntity> studentKey = Key.create(StudentEntity.class, id);
			Key<ClassEntity> classKey = classManager.register(code, studentKey);
			if(classKey != null)
				return register(student, classKey);
		}
		return false;
	}

	private boolean register(StudentEntity studentEntity, Key<ClassEntity> classKey) {
		
		try{
			List<Key<ClassEntity>> classKeys = studentEntity.getClasses();
			if(classKeys == null)
				classKeys = new ArrayList<>();
			if(!classKeys.contains(classKey))
			{
				classKeys.add(classKey);
				studentEntity.setClasses(classKeys);
				update(studentEntity);
				return true; 
			}
			
		}catch(Exception exception){
			
		}
		
		return false;
	}

	
}
