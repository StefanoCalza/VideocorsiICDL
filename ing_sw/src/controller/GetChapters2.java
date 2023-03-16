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

import org.apache.commons.lang.StringEscapeUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.*;
import dao.Chapter_CourseDao;
import immutablebeans.ImmutableChapter;
import immutablebeans.ImmutableCourse;
import immutablebeans.ImmutableUser;
import utils.ConnectionHandler;

@WebServlet("/GetChapters2")
public class GetChapters2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public GetChapters2() {
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
		ImmutableCourse c;
		Chapter_CourseDao chapterDao = new Chapter_CourseDao(connection);
		List<ImmutableChapter> chapters = new ArrayList<ImmutableChapter>();
		// get the list of chapter in a course
		try {
			chapters = chapterDao.chapterby(Integer.parseInt(request.getParameter("CourseId")));
			if (chapters == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
				return;
			}
			c = chapterDao.course_name(Integer.parseInt(request.getParameter("CourseId")));
		} catch (NumberFormatException | NullPointerException | SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db error");
			return;
		}

		request.setAttribute("namec", c.getName());
		request.setAttribute("chapter", chapters);
		request.getRequestDispatcher("WEB-INF/test4.jsp").forward(request, response);
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
