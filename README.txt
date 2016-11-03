S4


(Super Simple Scheduling System)


Summary:
Create an REST API to for a system that assigns students to classes.  API will be used by both a UI and programmatically by other systems.


(required) java code via a github repository.
(required) a short write-up  around what technologies/frameworks you are/would use in implementing various parts/tiers of this system
(optional) deployable/runnable war/jar
(optional) API documentation


Timeframe:
The scope of the exercise is somewhat fluid so do not spend more than 8 hours on it. 


Detailed Requirements:


Models:
Student = { student id, last name, first name }
Class = { code, title, description }
Student can attend unlimited number of classes.  Classes can have unlimited number of students


Operations:
Create/Edit/Delete Student
Create/Edit/Delete Class
Browse list of all Student
Browse list of all Classes
View all Students assigned to a Class
View all Classes assigned to a Student
Search Student/Classes by available fields/associations


Security:
None


Error Handling:
Does not need to be thorough.  Just enough to demonstrate how you would handle various types of errors (business, system)


Persistence:
Not part of the evaluation.  Feel free to mock it if that’s faster.


