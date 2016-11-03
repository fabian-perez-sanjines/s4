package com.s4.data.access.layer;

import java.util.ArrayList;
import java.util.List;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Result;
import com.s4.entity.ClassEntity;
import com.s4.entity.StudentEntity;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class ClassManager 
{
	static{
		ObjectifyService.register(ClassEntity.class);
	}
	
	public ClassManager(){
		
	}

	public ClassEntity get(Long code){
		ClassEntity classEntity = ofy().load().type(ClassEntity.class).id(code).now();
		return classEntity;
	}
	
	
	public List<ClassEntity> get(){
		List<ClassEntity> classEntity = ofy().load().type(ClassEntity.class).list();
		
		return classEntity;
	}
	
	public ClassEntity store(ClassEntity classEntity){
		ofy().save().entity(classEntity).now();
		return classEntity;
	}
	
	public ClassEntity update(ClassEntity classEntity){
		ClassEntity storedClass = get(classEntity.getCode());
		if(storedClass == null)
			return null;
		Result<Key<ClassEntity>> result = ofy().save().entity(classEntity);
		result.now();
		return classEntity;
	}

	public boolean delete(Long code) {
		
		ClassEntity classEntity = get(code);
		if(classEntity == null)
			return false;
		try{
			ofy().delete().type(ClassEntity.class).id(code);
		}catch(Exception exception){
			return false;
		}
		return true;
	}
	
	public List<ClassEntity> getByIds(List<Key<ClassEntity>> keys) {
		return new ArrayList<ClassEntity>(ofy().load().keys(keys).values());
	}
	
	public List<StudentEntity> getStudents(Long code) {
		ClassEntity classEntity = get(code);
		if(classEntity == null)
			return null;
		StudentManager studentManager = new StudentManager();
		return studentManager.getByIds(classEntity.getStudents());
	}
	
	public Key<ClassEntity> register(Long code, Key<StudentEntity> id) {
		ClassEntity classEntity = get(code);
		if(classEntity != null)
		{
			try{
				List<Key<StudentEntity>> studentsKeys = classEntity.getStudents();
				if(studentsKeys == null)
					studentsKeys = new ArrayList<>();
				if(!studentsKeys.contains(id))
				{
					studentsKeys.add(id);
					classEntity.setStudents(studentsKeys);
					update(classEntity);
					return Key.create(ClassEntity.class, code); 
				}
				
			}catch(Exception exception){
				
			}
		}
		return null;
	}
}
