package cinema.database;

/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cinema.util.Utils;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CinemaDAOImpl implements CinemaDAO {
	public EntityManager em = null;

	public CinemaDAOImpl(EntityManager em) {
		this.em = em;
	}

	// List with movies being showed and the halls

	public Showing getShowing(long showingId) {
		return em.find(Showing.class, showingId);
	}

	public void deleteShowing(long showingId) {
		Showing showing = getShowing(showingId);
		removeEntity(showing);
	}

	public void updateShowing(Showing showingToUpdate) {
		em.merge(showingToUpdate);
	}

	public void createShowing(Showing newShowing) {
		insertEntity(newShowing);
	}

	public void createReservation(Reservation newReservation) {
		insertEntity(newReservation);
	}

	public List<Showing> getShowingForMovie(String movieTitle) {
		TypedQuery<Showing> selectQuery = em.createQuery(
				"SELECT s FROM Showing s WHERE s.movie.movieTitle = :param1",
				Showing.class);
		selectQuery.setParameter("param1", movieTitle);
		List<Showing> allShowings = selectQuery.getResultList();
		return allShowings;
	}

	public List<Reservation> getReservationsForUser(String username) {
		TypedQuery<Reservation> selectQuery = em
				.createQuery(
						"SELECT r FROM Reservation r WHERE r.person.username = :param1",
						Reservation.class);
		selectQuery.setParameter("param1", username);
		List<Reservation> allReservations = selectQuery.getResultList();
		return allReservations;
	}

	public List<Reservation> getCurrentReservationsForUser(String username,
			String movieTitle) {
		TypedQuery<Reservation> selectQuery = em
				.createQuery(
						"SELECT r FROM Reservation r WHERE r.person.username = :param1 and r.showing.movie.movieTitle = :param2",
						Reservation.class);
		selectQuery.setParameter("param1", username);
		selectQuery.setParameter("param2", movieTitle);
		List<Reservation> allReservations = selectQuery.getResultList();
		return allReservations;
	}

	public List<Showing> getShowings() {
		TypedQuery<Showing> selectQuery = em.createQuery(
				"SELECT s FROM Showing s", Showing.class);
		List<Showing> allShowings = selectQuery.getResultList();
		return allShowings;
	}

	public Movie getMovie(long movieId) {
		return em.find(Movie.class, movieId);
	}

	public List<Movie> getMovies() {
		TypedQuery<Movie> selectQuery = em.createQuery("SELECT m FROM Movie m",
				Movie.class);
		List<Movie> allMovies = selectQuery.getResultList();
		return allMovies;
	}

	public Person getPerson(long personId) {
		return em.find(Person.class, personId);
	}

	public Person getPerson(String username) {
		TypedQuery<Person> selectQuery = em.createQuery(
				"SELECT p FROM Person p WHERE p.username = :param1",
				Person.class);
		selectQuery.setParameter("param1", username);
		return Utils.getSingleResultOrNull(selectQuery);
	}

	public void deletePerson(long personId) {
		Person person = getPerson(personId);
		removeEntity(person);
	}

	public void updatePerson(Person personToUpdate) {
		em.merge(personToUpdate);
	}

	public void createPerson(Person newPerson) {
		Person test = getPerson(newPerson.getPersonID());
		insertEntity(newPerson);
	}

	public String getRoleForPerson(String username) {
		TypedQuery<String> selectQuery = em.createQuery(
				"SELECT m.role FROM UserRoles m WHERE m.username=:param1",
				String.class);
		selectQuery.setParameter("param1", username);
		String role = selectQuery.getSingleResult();
		return role;

	}

	public void createRoleForPerson(UserRoles role) {
		insertEntity(role);
	}

	synchronized public boolean reserveSeats(long[] seatIDs, Showing showing,
			String username) {

		if (!checkSeats(seatIDs)) {
			return false;
		}

		for (int i = 0; i < seatIDs.length; i++) {
			Seats seat = getSeats(seatIDs[i]);
			Person person = getPerson(username);
			Reservation reservation = new Reservation(person, showing, seat);
			reservation.setIsPaid(true);
			createReservation(reservation);
			seat.setOccupied(true);
		}

		return true;
	}

	private boolean checkSeats(long[] seatIDs) {
		for (int i = 0; i < seatIDs.length; i++) {
			if (getSeats(seatIDs[i]).isOccupied()) {
				return false;
			}
		}
		return true;
	}

	public Seats getSeats(long seatsId) {
		return em.find(Seats.class, seatsId);
	}

	protected void insertEntity(Object entity) {
		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.persist(entity);
			transaction.commit();
		} catch (PersistenceException exc) {
			System.out.println(exc.getMessage());
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	public void fillTestData() {
		try {
			initiateCinema();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Books library initialized.");
	}

	protected void removeEntity(Object entity) {
		try {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(entity);
			transaction.commit();
		} catch (PersistenceException exc) {
			System.out.println(exc.getMessage());
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	private void initiateCinema() {
		Person p = new Person();
		p.setUsername("cnow");
		p.setPassword("ag4594");

		UserRoles role = new UserRoles();
		role.setRole("user");
		role.setUsername(p.getUsername());

		p.setRoles(role);
		insertEntity(p);
		/*
		 * Cinema cinema = new Cinema("TU-Cinema",
		 * "Sofia Carigradsko shose 115", 3592123456l); insertEntity(cinema);
		 * 
		 * Person user1 = new Person("user1", "John Smith", new Date(1989, 1,
		 * 1), "johnsmith@gmail.com", "secret", "test?", "test", "user");
		 * insertEntity(user1); Person admin1 = new Person("admin1",
		 * "Peter Stone", new Date(1989, 5, 5), "peterstone@gmail.com",
		 * "secret", "test?", "test", "admin"); insertEntity(admin1);
		 * 
		 * Movie movie1 = new Movie("Skyfall", "action",
		 * "Daniel Craig, Javier Bardem, Naomie Harris", "Sam Mendes", 2012,
		 * "USA", 143, "C", "link", "../images/posters/skyfall.jpg");
		 * insertEntity(movie1); Movie movie2 = new
		 * Movie("The Hobbit: An Unexpected Journey", "fantasy",
		 * "Martin Freeman, Ian McKellen and Richard Armitage", "Peter Jackson",
		 * 2012, "USA", 169, "B", "link", "../images/posters/Hobbit.jpg");
		 * insertEntity(movie2); Movie movie3 = new Movie("Jack Reacher",
		 * "action", "Tom Cruise, Rosamund Pike and Richard Jenkins",
		 * "Christopher McQuarrie", 2012, "USA", 130, "C", "link",
		 * "../images/posters/jackreacher.jpg"); insertEntity(movie3); Movie
		 * movie4 = new Movie("Finding Nemo 3D", "animation",
		 * "Ellen DeGeneres, Willem Dafoe and Brad Garrett", "Andrew Stanton",
		 * 2013, "USA", 100, "A", "link", "../images/posters/Finding-Nemo.jpg");
		 * insertEntity(movie4); Movie movie5 = new Movie("Monsters Inc 3D",
		 * "animation", "Billy Crystal, John Goodman and Mary Gibbs",
		 * "Pete Docter", 2012, "USA", 109, "A", "link",
		 * "../images/posters/monstersinc.jpg"); insertEntity(movie5);
		 * 
		 * Hall hall1 = new Hall(1, 10, 2, cinema); insertEntity(hall1); Hall
		 * hall2 = new Hall(2, 15, 3, cinema); insertEntity(hall2); Hall hall3 =
		 * new Hall(3, 15, 3, cinema); insertEntity(hall3); Hall hall4 = new
		 * Hall(4, 15, 3, cinema); insertEntity(hall4); Hall hall5 = new Hall(5,
		 * 15, 3, cinema); insertEntity(hall5);
		 * 
		 * Showing showing1 = new Showing(new java.util.Date(2013, 1, 30, 10,
		 * 10), movie1, hall1); insertEntity(showing1); Showing showing2 = new
		 * Showing(new java.util.Date(2013, 1, 30, 11, 10), movie2, hall2);
		 * insertEntity(showing2); Showing showing3 = new Showing(new
		 * java.util.Date(2013, 1, 30, 12, 10), movie3, hall3);
		 * insertEntity(showing3); Showing showing4 = new Showing(new
		 * java.util.Date(2013, 1, 30, 13, 10), movie4, hall4);
		 * insertEntity(showing4); Showing showing5 = new Showing(new
		 * java.util.Date(2013, 1, 30, 14, 10), movie5, hall5);
		 * insertEntity(showing5);
		 * 
		 * Seats seat1 = new Seats(1, 1, false, hall1); insertEntity(seat1);
		 * Seats seat2 = new Seats(2, 1, false, hall1); insertEntity(seat2);
		 * Seats seat3 = new Seats(3, 1, false, hall1); insertEntity(seat3);
		 * Seats seat4 = new Seats(4, 1, false, hall1); insertEntity(seat4);
		 * Seats seat5 = new Seats(5, 1, false, hall1); insertEntity(seat5);
		 * Seats seat6 = new Seats(1, 2, false, hall1); insertEntity(seat6);
		 * Seats seat7 = new Seats(2, 2, false, hall1); insertEntity(seat7);
		 * Seats seat8 = new Seats(3, 2, false, hall1); insertEntity(seat8);
		 * Seats seat9 = new Seats(4, 2, true, hall1); insertEntity(seat9);
		 * Seats seat10 = new Seats(5, 2, false, hall1); insertEntity(seat10);
		 * 
		 * Reservation reservation1 = new Reservation(user1, showing1, seat1);
		 * insertEntity(reservation1);
		 * 
		 * UserRoles role1 = new UserRoles("user1", "user");
		 * insertEntity(role1); UserRoles role2 = new UserRoles("admin1",
		 * "admin"); insertEntity(role2);
		 */
	}

	// ----- HELPER methods
	// --------------------------------------------------------------
	// private void addAuthorToDatabaseIfMissing(Author author) throws
	// InvalidAuthorException {
	// if (!authorExists(author.getId())) {
	// createAuthor(author);
	// } else {
	// System.out.println("Author [" + author.getFirstName() + " "
	// + author.getLastName() + "] is already in database.");
	// }
	// }
	//
	// private void addBookToDatabaseIfMissing(Movie book) {
	// if (!bookExists(book.getBookId())) {
	// insertEntity(book);
	// System.out.println("Book [" + book.getTitle()
	// + "] added to database");
	// } else {
	// System.out.println("Book [" + book.getTitle()
	// + "] is already in database");
	// }
	// }
	public void destroy() {
		throw new NotImplementedException();
	}

}
