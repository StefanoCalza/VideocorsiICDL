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
import dao.QuizDAO;
import immutablebeans.ImmutableCourse;
import immutablebeans.ImmutableU_C;
import immutablebeans.ImmutableUser;
import utils.ConnectionHandler;

@WebServlet("/GetCourse")
public class GetCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetCourse() {
		super();
	}

	public void init() throws ServletException {
		connection = ConnectionHandler.getConnection(getServletContext());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		ImmutableUser user = (ImmutableUser) session.getAttribute("user");

		Chapter_CourseDao chapterDao = new Chapter_CourseDao(connection);
		List<ImmutableCourse> courses = new ArrayList<ImmutableCourse>();
		List<ImmutableCourse> courses_not_in = new ArrayList<ImmutableCourse>();
		// get the list of course followed and not followed by the user
		try {
			courses = chapterDao.Course_by_id(user.getId());
			courses_not_in = chapterDao.Course_by_id_notin(user.getId());
			if (courses == null || courses_not_in == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
				return;
			}
		} catch (NumberFormatException | NullPointerException | SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db error");
			return;
		}
		if (user.getRole() == 2) {
			request.setAttribute("chapter", courses);

			request.setAttribute("coursesnotin", courses_not_in);

			request.getRequestDispatcher("courses.jsp").forward(request, response);

		} else {
			QuizDAO quizDao = new QuizDAO(connection);
			List<ImmutableU_C> u_c = new ArrayList<ImmutableU_C>();
			try {
				u_c = quizDao.quiz_to_verify();
				if (u_c == null) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
					return;
				}
			} catch (NumberFormatException | NullPointerException | SQLException e) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db error");
				return;
			}
			request.setAttribute("userchapter", u_c);
			request.getRequestDispatcher("homeDocente.jsp").forward(request, response);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
