/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */

package cinema.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cinema.database.CinemaDAO;
import cinema.database.CinemaDAOImpl;

public final class ContextListener implements ServletContextListener {

	private ServletContext context = null;

	public void contextInitialized(ServletContextEvent event) {
		try {
			context = event.getServletContext();
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("cinemaDatabase");
			CinemaDAO cinemaDBAO = new CinemaDAOImpl(emf.createEntityManager());
			context.setAttribute("test", "test");
			context.setAttribute("cinemaDBAO", cinemaDBAO);
		} catch (Exception ex) {
			System.err.println("Couldn't create bookstore database bean: "
					+ ex.getMessage());
			ex.printStackTrace(System.err);
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		context = event.getServletContext();
		CinemaDAO cinemaDBAO = (CinemaDAOImpl) context.getAttribute("cinemaDBAO");
		cinemaDBAO.destroy();
		context.removeAttribute("cinemaDBAO");
	}

}
