<%@page import="java.util.List"%>
<%@page import="cinema.database.Showing"%>
<%@page import="cinema.database.Reservation"%>
<%@page import="cinema.database.Movie"%>
<%@page import="cinema.database.Hall"%>
<%@page import="cinema.database.Person"%>
<%@page import="cinema.database.CinemaDAOImpl"%>
<%@page import="cinema.database.CinemaDAO"%>
<%@page import="cinema.listeners.ContextListener"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.Persistence"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<title>Untitled Page</title>
<link
	href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,700"
	rel="stylesheet" type="text/css" />
<link href="../css/default.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.3.js" type="text/javascript"></script>
<script src="../js/JQueryCheckin.js" type="text/javascript"></script>
</head>

<%
	CinemaDAO dao = (CinemaDAO) application.getAttribute("cinemaDBAO");
	List<Movie> movies = dao.getMovies();
	String role = "";
%>

<body>
<div id="header" class="container">
		<div id="logo">
			<h1>
				<a class="logogreet">Welcome to TU Cinema!</a>
			</h1>
		</div>
		<div id="menu">
			<div id="greeting">
				<%
					if (request.getUserPrincipal() != null) {
						role = dao.getRoleForPerson(request.getUserPrincipal()
								.getName());
				%>
				<p>
					Hello,
					<%=request.getUserPrincipal().getName()%></p>
				<a href="/TicketSystem/authentication/logout.jsp">Click here to
					log out</a>
				<%
					} else {
				%>
				<p>Hello, guest</p>
				<a href="/TicketSystem/authentication/register.jsp">Register</a>
				<%
					}
				%>
			</div>
			<ul>
				<li><div style="display: inline">
						<a onclick="test(this);" href="/TicketSystem/general/homepage.jsp">Homepage</a>
					</div></li>
				<li><div style="display: inline">
						<a onclick="test(this);"
							href="/TicketSystem/showings/listshowings.jsp">Showings</a>
					</div></li>

				<li><div style="display: inline">
						<a onclick="test(this);"
							href="/TicketSystem/useraccess/reservationsreview.jsp">My
							reservations</a>
					</div></li>

				<%
					if (role.equals("admin")) {
				%>
				<li><div style="display: inline">

						<a onclick="test(this);"
							href="/TicketSystem/administration/usermanagement.jsp">Manage
							users</a>
					</div></li>
				<%
					}
				%>
			</ul>
		</div>
	</div>
	
	<div id="page" class="container">
		<div id="back-top" style="display: none;">
			<a href="#content"> <span></span> Back to top
			</a>
		</div>
		<div id="content">

	<h3>Showing's tickets</h3>
	
	<%
			if(!(request.getParameter("selected_movie") == null || request.getParameter("selected_movie").isEmpty()) &&
					!(request.getParameter("selected_user") == null || request.getParameter("selected_user").isEmpty())) { 
				
				if(!(request.getParameter("reservation") == null || request.getParameter("reservation").isEmpty())){
					Person person = dao.getPerson(request.getParameter("selected_user"));
					List<Reservation> tempReservations = dao.getCurrentReservationsForUser(request.getParameter("selected_user"), request.getParameter("selected_movie"));
					for(Reservation tempReservation : tempReservations){
						if(request.getParameter("reservation").equals(String.valueOf(tempReservation.getReservationID()))) {
							tempReservation.setIsUsed(true);
						}
					}
				}
				

				List<Reservation> reservations = dao.getCurrentReservationsForUser(request.getParameter("selected_user"), request.getParameter("selected_movie"));
				if(reservations != null && !reservations.isEmpty()) {
		%>

				<table class="infotable">
					<tr>
						<th>User</th>
						<th>Movie</th>
						<th>Hall Nr</th>
						<th>Seat Nr</th>
						<th>Time</th>
						<th>Payed</th>
						<th>Used</th>
						<th>Submit presence</th>
					<tr>
			
			<%
			

				for(Reservation reservation : reservations){
    %>
					<tr>
						<td><%=reservation.getPerson().getUsername()%></td>
						<td><%=reservation.getShowing().getMovie().getMovieTitle()%></td>
						<td><%=reservation.getShowing().getHall().getHallNumber()%></td>
						<td><%=reservation.getSeat().getSeatNumber()%></td>
						<td><%=reservation.getShowing().getTime()%></td>
					<%if(reservation.getIsPaid()) {%>
						
						<td>yes</td>
					<%} else {%>
						<td>no</td>
					<%} %>
					<%if(reservation.getIsUsed()) {%>
						
						<td>yes</td>
						<td></td>
					<%} else {%>
						<td>no</td>
						<td><a href="checkinuser.jsp?selected_user=<%=request.getParameter("selected_user")%>&selected_movie=<%=request.getParameter("selected_movie")%>&reservation=<%=reservation.getReservationID()%>"><button class="submitButton">Present</button></a></td>
					<%} %>
						
					</tr>
				
<%
				}
			 %></table> <%
				}
			else
			{
		%>
					<h2>No reservations found for movie "<%=request.getParameter("selected_movie") %>" for user "<%=request.getParameter("selected_user") %>"</h2>
		<%
			}
		}
	%>

	<h3>Select movie</h3>
	<select id="movieSelect">
	<%
		for (Movie movie : movies) {
	%>
			<option><%=movie.getMovieTitle()%></option>
	<%
		}
	%>
	</select>
	
	<h3>Select user</h3>
	
	<input type="text" id="username" >
	
	<a id="showInfoButton" href="checkinuser.jsp"><button id=userinfo>Show info</button></a>


		</div>
		<div id="footer" class="container">
			<p>Copyright (c) 2013 TUCinema.com. All rights reserved. Design
				by TU.</p>
		</div>
	</div>
</body>
</html>