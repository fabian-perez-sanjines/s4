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
import com.s4.controller.StudentController;
import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;

@Path("/student")
@Produces("application/json")
public class StudentService {
	
	private StudentController studentController;
	
	public StudentService()
	{
		studentController = new StudentController();
	}
	 
    @GET
    public Response get() {
        return Response.status(200).entity(studentController.get()).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getByID(@PathParam("id") Long id) {
    	Response response = Response.status(404).build();
    	StudentMapper student = studentController.get(id);
    	if(student != null)
    		response = Response.status(200).entity(student).build();
    	return response;
    }
    
    @POST
    @Consumes("application/json")
    public Response post(StudentMapper student) {
    	return Response.status(200).entity(studentController.create(student)).build();
    }
    
    
    @POST
    @Path("/{id}/register/{code}")
    public Response post(@PathParam("id") Long id, @PathParam("code") Long code) {
    	Response response = Response.status(404).build();
    	if(studentController.registerClass(id, code))
    		response = Response.status(200).entity("REGISTERED").build();
    	return response;
    }
    
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response put(@PathParam("id") Long id, StudentMapper student) {
    	Response response = Response.status(404).build();
    	StudentMapper studentResponse = studentController.update(id, student);
    	if(studentResponse != null)
    		response = Response.status(200).entity(studentResponse).build();
    	return response;
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
    	Response response = Response.status(404).build();
    	if(studentController.delete(id))
    		response = Response.status(200).entity("DELETED").build();
    	return response;
    }
    
    @GET
    @Path("/{id}/classes")
    public Response getStudentses(@PathParam("id") Long id) {
    	Response response = Response.status(404).build();
    	List<ClassMapper> students = studentController.getClasses(id);
    	if(students != null)
    		response = Response.status(200).entity(students).build();
    	return response;
    }

	public StudentController getStudentController() {
		return studentController;
	}

	public void setStudentController(StudentController studentController) {
		this.studentController = studentController;
	}
}

