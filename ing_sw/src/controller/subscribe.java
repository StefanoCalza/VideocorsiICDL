package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import immutablebeans.ImmutableUser;
import utils.ConnectionHandler;

@WebServlet("/Iscriviti")
public class subscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public subscribe() {
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
		List<Integer> list_chapter = new ArrayList<Integer>();
		// get the chapter of a course
		try {
			list_chapter = chapterDao.chapter_by_course(Integer.parseInt(request.getParameter("CourseId")));
			if (list_chapter == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
				return;
			}
		} catch (NumberFormatException | NullPointerException | SQLException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db error");
			return;
		}
		// subscribe the user to every chapter of a course
		try {
			for (Integer idchapter : list_chapter) {
				chapterDao.subscribe_user(Integer.parseInt(request.getParameter("CourseId")), user.getId(), idchapter);
			}

		} catch (NumberFormatException | NullPointerException | SQLException e) {
			// only for debugging
			//e.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "db error");
			return;
		}

		request.setAttribute("chapter", list_chapter);

		request.getRequestDispatcher("GetChapters2").forward(request, response);
	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
