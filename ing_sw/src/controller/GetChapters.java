package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


import beans.*;
import dao.Chapter_CourseDao;
import immutablebeans.ImmutableCourse;
import immutablebeans.ImmutableUser;
import utils.ConnectionHandler;

@WebServlet("/GetChapters")
public class GetChapters extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetChapters() {
		super();
	}

	public void init() throws ServletException {
		ServletContext servletContext = getServletContext();

		connection = ConnectionHandler.getConnection(getServletContext());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	

		HttpSession session = request.getSession();
		ImmutableUser user = (ImmutableUser) session.getAttribute("user");
		
		Chapter_CourseDao chapterDao = new Chapter_CourseDao(connection);
		List<ImmutableCourse> courses = new ArrayList<ImmutableCourse>();
		//get the list of course followed by the user
		try {
			courses = chapterDao.Course_by_id(user.getId());
			
			if (courses == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
				return;
			}}
			catch (NumberFormatException | NullPointerException | SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db error");
				e.printStackTrace();
				return;
			}
			
		request.setAttribute("chapter", courses);
		request.getRequestDispatcher("WEB-INF/test3.jsp").forward(request, response);
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
