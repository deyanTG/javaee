package cinema.database;

/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */

import java.util.List;

public interface CinemaDAO {

	
	public Showing getShowing(long showingId);
	
	public void deleteShowing(long showingId);

	public void updateShowing(Showing showingToUpdate);

	public void createShowing(Showing newShowing);
	
	public Movie getMovie(long movieId);
	
	public Seats getSeats(long seatsId);
	
	public boolean reserveSeats(long[] seatIDs, Showing showing, String username);
	
	public Person getPerson(long personId);
	
	public Person getPerson(String username) ;
	
	public void deletePerson(long personId);

	public void updatePerson(Person personToUpdate);

	public void createPerson(Person newPerson);
	
	public String getRoleForPerson(String username);

	public void createRoleForPerson(UserRoles roleToUpdate);
	
	public List<Showing> getShowingForMovie(String movieTitle) ;
	
	public List<Reservation> getCurrentReservationsForUser(String username, String movieTitle);
	
	public List<Reservation> getReservationsForUser(String username) ;
	
	public List<Showing> getShowings() ;
	
	public List<Movie> getMovies() ;
	

	// @Override
	// public Author getAuthor(long authorId) throws AuthorNotFoundException {
	// return em.find(Author.class, authorId);
	// }
	//
	// public Movie getBook(long bookId) throws BookNotFoundException {
	// return em.find(Movie.class, bookId);
	// }
	//
	// public void deleteBook(long bookId) throws BookNotFoundException {
	// Movie book = getBook(bookId);
	// removeEntity(book);
	// }
	//
	// public void buyBooks(ShoppingCart cart) throws OrderException {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public void updateBook(Movie bookToUpdate) throws BookNotFoundException,
	// InvalidBookException {
	// em.merge(bookToUpdate);
	// }
	//
	// @Override
	// public void createBook(Movie newBook) throws BookAlreadyExistsException,
	// InvalidBookException {
	// insertEntity(newBook);
	// }
	//
	// public void buyBook(long bookId, int quantity) throws OrderException {
	// throw new NotImplementedException();
	// }
	//
	// public void fillBookstore() {
	// try {
	// initiateBooks();
	// } catch (InvalidAuthorException e) {
	// System.out.println(e.getMessage());
	// }
	// System.out.println("Books library initialized.");
	// }
	//
	// public boolean authorExists(long authorId) {
	// throw new NotImplementedException();
	// }
	//
	// public boolean bookExists(long bookId) {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public List<Movie> getBooks() {
	// TypedQuery<Movie> selectQuery = em.createQuery("SELECT b FROM Book b",
	// Movie.class);
	// List<Movie> allBooks = selectQuery.getResultList();
	// return allBooks;
	// }
	//
	// @Override
	// public List<Author> getAuthors() {
	// return Collections.emptyList();
	// }
	//
	// @Override
	// public void updateAuthor(Author author) throws AuthorNotFoundException,
	// InvalidAuthorException {
	// throw new NotImplementedException();
	// }
	//
	// //delete
	// //TypedQuery<Author> queryAuthors = em.createQuery ... where a.firstNane
	// = :param1 ...
	// //queryAuthors.setParameter("param1", firstName);
	// //
	//
	// @Override
	// public void deleteAuthor(String firstName, String lastName)
	// throws AuthorNotFoundException {
	// throw new NotImplementedException();
	// }
	//
	// @Override
	// public void deleteAuthor(long authorId) throws AuthorNotFoundException {
	// Author author = getAuthor(authorId);
	// removeEntity(author);
	// }
	//
	// @Override
	// public void createAuthor(Author author) throws InvalidAuthorException {
	// insertEntity(author);
	// }
	//
	//
	// public void destroy() {
	// throw new NotImplementedException();
	// }


	public void fillTestData();


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
	public void destroy() ;


}
