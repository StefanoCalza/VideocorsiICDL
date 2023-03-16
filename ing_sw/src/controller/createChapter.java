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
import javax.sound.midi.Soundbank;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import beans.*;
import dao.Chapter_CourseDao;
import dao.QuizDAO;
import immutablebeans.ImmutableUser;
import utils.ConnectionHandler;

@WebServlet("/CreateChapter")
public class createChapter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public createChapter() {
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
		Chapter_CourseDao courseDao = new Chapter_CourseDao(connection);
		QuizDAO quizDao = new QuizDAO(connection);
		int id = 0;
		int id_quiz = 0;
		// insert chapter and quiz 
		try {

			id = courseDao.maxchapterby_id(Integer.parseInt(request.getParameter("CourseId")));
			courseDao.insert_chapter(Integer.parseInt(request.getParameter("CourseId")), id,
					request.getParameter("Chaptername"), request.getParameter("Video"),
					Integer.parseInt(request.getParameter("isfinal")), request.getParameter("Chapterdescription"));
			id_quiz = quizDao.maxquiz();
			quizDao.insert_quiz(id_quiz, request.getParameter("d1"), request.getParameter("r11"),
					request.getParameter("r12"), request.getParameter("r13"), request.getParameter("r14"),
					Integer.parseInt(request.getParameter("g1")), Integer.parseInt(request.getParameter("CourseId")),
					id);
			quizDao.insert_quiz(id_quiz + 1, request.getParameter("d2"), request.getParameter("r21"),
					request.getParameter("r22"), request.getParameter("r23"), request.getParameter("r24"),
					Integer.parseInt(request.getParameter("g2")), Integer.parseInt(request.getParameter("CourseId")),
					id);
			quizDao.insert_quiz(id_quiz + 2, request.getParameter("d3"), request.getParameter("r31"),
					request.getParameter("r32"), request.getParameter("r33"), request.getParameter("r34"),
					Integer.parseInt(request.getParameter("g3")), Integer.parseInt(request.getParameter("CourseId")),
					id);
			quizDao.insert_quiz(id_quiz + 3, request.getParameter("d4"), request.getParameter("r41"),
					request.getParameter("r42"), request.getParameter("r43"), request.getParameter("r44"),
					Integer.parseInt(request.getParameter("g4")), Integer.parseInt(request.getParameter("CourseId")),
					id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("name_course", request.getParameter("name_course"));
		request.setAttribute("description_corse", request.getParameter("description_corse"));
		request.setAttribute("id_course", request.getParameter("CourseId"));
		if (Integer.parseInt(request.getParameter("isfinal")) == 0) {
			request.getRequestDispatcher("Create_chapter.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("homeDocente.jsp").forward(request, response);
		}

	}

	public void destroy() {
		try {
			ConnectionHandler.closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
