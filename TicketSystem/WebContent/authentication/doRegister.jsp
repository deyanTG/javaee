<%@page import="cinema.database.UserRoles"%>
<%@ page import="java.sql.*"%>
<%@page import="cinema.database.CinemaDAOImpl"%>
<%@page import="cinema.database.CinemaDAO"%>
<%@page import="cinema.database.Person"%>
<%
	CinemaDAO dao = (CinemaDAO) application.getAttribute("cinemaDBAO");

	String str1 = request.getParameter("t1");
	String str2 = request.getParameter("t2");
	String str3 = request.getParameter("t3");
	Person person = new Person();
	person.setUsername(str1);
	person.setPassword(str2);
	UserRoles role = new UserRoles();
	role.setRole("user");
	role.setUsername(person.getUsername());
	person.setRoles(role);
	dao.createPerson(person);
%>
<jsp:forward page="successful_registration.jsp"></jsp:forward>

