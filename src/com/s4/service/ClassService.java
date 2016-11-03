package com.s4.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.s4.controller.ClassController;
import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;

@Path("/class")
@Produces("application/json")
public class ClassService {
	
	private ClassController classController;
	
	public ClassService()
	{
		classController = new ClassController();
	}
	 
    @GET
    public Response get() {
        return Response.status(200).entity(classController.get()).build();
    }
    
    @GET
    @Path("/{code}")
    public Response getByID(@PathParam("code") Long code) {
    	Response response = Response.status(404).build();
    	ClassMapper classMapper = classController.get(code);
    	if(classMapper != null)
    		response = Response.status(200).entity(classMapper).build();
    	return response;
    }
    
    @POST
    @Consumes("application/json")
    public Response post(ClassMapper classMapper) {
    	return Response.status(200).entity(classController.create(classMapper)).build();
        
    }
    
    @PUT
    @Path("/{code}")
    @Consumes("application/json")
    public Response put(@PathParam("code") Long code, ClassMapper classMapper) {
    	Response response = Response.status(404).build();
    	ClassMapper classResponse = classController.update(code, classMapper);
    	if(classResponse != null)
    		response = Response.status(200).entity(classResponse).build();
    	return response;
    }
    
    @DELETE
    @Path("/{code}")
    public Response delete(@PathParam("id") Long code) {
    	Response response = Response.status(404).build();
    	if(classController.delete(code))
    		response = Response.status(200).entity("DELETED").build();
    	return response;
    }
    
    @GET
    @Path("/{code}/students")
    public Response getStudentses(@PathParam("code") Long code) {
    	Response response = Response.status(404).build();
    	List<StudentMapper> students = classController.getStudents(code);
    	if(students != null)
    		response = Response.status(200).entity(students).build();
    	return response;
    }

	public ClassController getClassController() {
		return classController;
	}

	public void setClassController(ClassController classController) {
		this.classController = classController;
	}
    
}

