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
import com.s4.service.StudentService;

/**
 * Class in charge of publish student services, this class interact with the
 * controller and produces JSON, the url is /api/student
 * 
 * @author Fabian Perez
 * 
 */
@Path("/student")
@Produces("application/json")
public class StudentController {

	private StudentService studentService;

	/**
	 * Constructor that instantiate studentController.
	 */
	public StudentController() {
		studentService = new StudentService();
	}

	/**
	 * Service to get all id that matches the query params.
	 * 
	 * @param firstname
	 *            filter by firstname
	 * @param lastname
	 *            filter by lastname
	 * @return JSON response with the list of students
	 */
	@GET
	public Response get(@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname) {
		Map<String, String> params = new HashMap<>();
		if (firstname != null)
			params.put("firstname", firstname);
		if (lastname != null)
			params.put("lastname", lastname);
		return Response.status(200).entity(studentService.get(params)).build();
	}

	/**
	 * Service to get the student that matches the given id.
	 * 
	 * @param id
	 *            the id to recover the class.
	 * @return JSON response with the student.
	 */
	@GET
	@Path("/{id}")
	public Response getByID(@PathParam("id") Long id) {
		Response response = Response.status(404).build();
		StudentMapper student = studentService.get(id);
		if (student != null)
			response = Response.status(200).entity(student).build();
		return response;
	}

	/**
	 * Service to create the given student.
	 * 
	 * @param classMapper
	 *            the student to be created.
	 * @return JSON response student with the generated id.
	 */
	@POST
	@Consumes("application/json")
	public Response post(StudentMapper student) {
		return Response.status(200).entity(studentService.create(student)).build();
	}

	/**
	 * Service to register the student with the given id in the class with the
	 * given code.
	 * 
	 * @param id
	 *            to find the student
	 * @param code
	 *            to find the class
	 * @return REGISTERED if it worked.
	 */
	@POST
	@Path("/{id}/register/{code}")
	public Response post(@PathParam("id") Long id, @PathParam("code") Long code) {
		Response response = Response.status(404).build();
		if (studentService.registerClass(id, code))
			response = Response.status(200).entity("REGISTERED").build();
		return response;
	}

	/**
	 * Service to update the given student that match the given id.
	 * 
	 * @param id
	 *            the id that will be used to search the student.
	 * @param student
	 *            the student to be updated.
	 * @return JSON response with the updated student.
	 */
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	public Response put(@PathParam("id") Long id, StudentMapper student) {
		Response response = Response.status(404).build();
		StudentMapper studentResponse = studentService.update(id, student);
		if (studentResponse != null)
			response = Response.status(200).entity(studentResponse).build();
		return response;
	}

	/**
	 * Service to delete a student given a id.
	 * 
	 * @param id
	 *            the id that will be used to delete the student.
	 * @return DELETED if the deletion was correct.
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Response response = Response.status(404).build();
		if (studentService.delete(id))
			response = Response.status(200).entity("DELETED").build();
		return response;
	}

	/**
	 * Service to obtain all classes that the student with the given id is
	 * registered.
	 * 
	 * @param id
	 *            the student id that will be used to obtain the student
	 *            classes.
	 * @return JSON response with the classes.
	 */
	@GET
	@Path("/{id}/classes")
	public Response getStudentses(@PathParam("id") Long id) {
		Response response = Response.status(404).build();
		List<ClassMapper> students = studentService.getClasses(id);
		if (students != null)
			response = Response.status(200).entity(students).build();
		return response;
	}

	public StudentService getStudentController() {
		return studentService;
	}

	public void setStudentController(StudentService studentController) {
		this.studentService = studentController;
	}
}
