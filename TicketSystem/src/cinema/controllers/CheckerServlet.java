package cinema.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cinema.database.CinemaDAO;

/**
 * Servlet implementation class CheckerServlet
 */
@WebServlet("/authentication/check")
public class CheckerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("-------");
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("uname");
		System.out.println(getServletContext().getAttribute("test"));
		CinemaDAO dao = (CinemaDAO) getServletContext().getAttribute(
				"cinemaDBAO");
		if (dao != null) {
			if (dao.getPerson(uname) == null) {
				out.println("<b>" + uname + "</b> is avaliable");
			} else {
				out.println("<font color=red><b>" + uname
						+ "</b> is already in use</font>");
			}
			out.println();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
