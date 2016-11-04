package com.s4.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.s4.model.ClassMapper;
import com.s4.model.StudentMapper;
import com.s4.service.ClassService;

/**
 * Class in charge of publish class services, this class interact with the
 * controller and produces JSON, the url is /api/class
 * 
 * @author Fabian Perez
 * 
 */
@Path("/class")
@Produces("application/json")
public class ClassController {

	private ClassService classService;

	/**
	 * Constructor that instantiate classController.
	 */
	public ClassController() {
		classService = new ClassService();
	}

	/**
	 * Service to get all classes that matches the query params.
	 * 
	 * @param title
	 *            filter by title
	 * @param description
	 *            filter by description
	 * @return JSON response with the list of classes
	 */
	@GET
	public Response get(@QueryParam("title") String title, @QueryParam("description") String description) {
		Map<String, String> params = new HashMap<>();
		if (title != null)
			params.put("title", title);
		if (description != null)
			params.put("description", description);
		return Response.status(200).entity(classService.get(params)).build();
	}

	/**
	 * Service to get the class that matches the given code.
	 * 
	 * @param code
	 *            the code to recover the class.
	 * @return JSON response with the class.
	 */
	@GET
	@Path("/{code}")
	public Response getByID(@PathParam("code") Long code) {
		Response response = Response.status(404).build();
		ClassMapper classMapper = classService.get(code);
		if (classMapper != null)
			response = Response.status(200).entity(classMapper).build();
		return response;
	}

	/**
	 * Service to create the given class.
	 * 
	 * @param classMapper
	 *            the class to be created.
	 * @return JSON response class with the generated code.
	 */
	@POST
	@Consumes("application/json")
	public Response post(ClassMapper classMapper) {
		return Response.status(200).entity(classService.create(classMapper)).build();

	}

	/**
	 * Service to update the given class that match the given code.
	 * 
	 * @param code
	 *            the code that will be used to search the class.
	 * @param classMapper
	 *            the class to be updated.
	 * @return JSON response with the updated class.
	 */
	@PUT
	@Path("/{code}")
	@Consumes("application/json")
	public Response put(@PathParam("code") Long code, ClassMapper classMapper) {
		Response response = Response.status(404).build();
		ClassMapper classResponse = classService.update(code, classMapper);
		if (classResponse != null)
			response = Response.status(200).entity(classResponse).build();
		return response;
	}

	/**
	 * Service to delete a class given a code.
	 * 
	 * @param code
	 *            the code that will be used to delete the class.
	 * @return DELETED if the deletion was correct.
	 */
	@DELETE
	@Path("/{code}")
	public Response delete(@PathParam("id") Long code) {
		Response response = Response.status(404).build();
		if (classService.delete(code))
			response = Response.status(200).entity("DELETED").build();
		return response;
	}

	/**
	 * Service to obtain all registered students in the class with the given
	 * code.
	 * 
	 * @param code
	 *            the class code that will be used to obtain the class students.
	 * @return JSON response with the students.
	 */
	@GET
	@Path("/{code}/students")
	public Response getStudents(@PathParam("code") Long code) {
		Response response = Response.status(404).build();
		List<StudentMapper> students = classService.getStudents(code);
		if (students != null)
			response = Response.status(200).entity(students).build();
		return response;
	}

	public ClassService getClassController() {
		return classService;
	}

	public void setClassController(ClassService classController) {
		this.classService = classController;
	}

}
