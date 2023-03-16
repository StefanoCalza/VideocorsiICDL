package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Chapter;
import beans.Course;
import beans.User;
import dao.Chapter_CourseDao;
import dao.UserDAO;
import immutablebeans.ImmutableChapter;
import immutablebeans.ImmutableCourse;
import utils.*;

//import org.apache.commons.lang.*;

public class chapterDaotest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public chapterDaotest() {
		super();
	}

	public void init() throws ServletException {
	}

	@BeforeEach
	void setUp() throws ServletException, Exception {
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_videocorsi", "root", "Fagianogio!2");
	}

	@Test
	public void Course_by_id_Test() throws SQLException { // input iduser
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		List<ImmutableCourse> courses = new ArrayList<ImmutableCourse>();
		courses = coursedao.Course_by_id(0);

		assertEquals(1,courses.get(0).getIdCourse());
		assertEquals("Competenze base PC",courses.get(0).getDescription());
		assertEquals("Computer Essential", courses.get(0).getName());

	}

	@Test
	public void Course_by_id_notin_Test() throws SQLException { // input iduser
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		List<ImmutableCourse> courses = new ArrayList<ImmutableCourse>();
		courses = coursedao.Course_by_id_notin(0);

		assertEquals(2, courses.get(0).getIdCourse());
		assertEquals("Online Essential", courses.get(0).getName());

	}

	@Test
	public void allchapter_Test() throws SQLException { 
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		List<ImmutableChapter> chapters = new ArrayList<ImmutableChapter>();
		chapters = coursedao.allchapter();


	}

	@Test
	public void chapterby_Test() throws SQLException { 
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		List<ImmutableChapter> chapters = new ArrayList<ImmutableChapter>();
		chapters = coursedao.chapterby(1);

		assertEquals(1,chapters.get(0).getIdChapter());
		assertEquals(1,chapters.get(0).getIdCourse());
		assertEquals("Computer e Dispositivi",chapters.get(0).getName());
		assertEquals("3tdtTkNMGvA",chapters.get(0).getVideo());
		assertEquals("Hardware e Software", chapters.get(0).getDescription());

		assertEquals(2,chapters.get(1).getIdChapter());
		assertEquals(1,chapters.get(1).getIdCourse());
		assertEquals("Gestione dei file",chapters.get(1).getName());
		assertEquals("3tdtTkNMGvA", chapters.get(1).getVideo());
		assertEquals("Organizzazione di file e cartelle", chapters.get(1).getDescription());

		assertEquals(3, chapters.get(2).getIdChapter());
		assertEquals(1,chapters.get(2).getIdCourse());
		assertEquals("Esame finale", chapters.get(2).getName());
		assertEquals("Test di valutazione", chapters.get(2).getDescription());

	}

	@Test
	public void maxCourse() throws SQLException {
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);

		int maxtocheck = coursedao.maxCourse();
		int maxtoverify = 0;
		String query = "SELECT MAX(idcourses) as idcourses from courses";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					maxtoverify = result.getInt("idcourses");
				}
			}
		}

		assertEquals( maxtoverify + 1, maxtocheck);

	}

	@Test
	public void maxchapterby_id() throws SQLException {
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		int id = 1;
		int maxtocheck = coursedao.maxchapterby_id(id);
		int maxtoverify = 0;
		String query = "SELECT MAX(chapter) as idchapter from chapter where course = ?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					maxtoverify = result.getInt("idchapter");
				}
			}
		}

		assertEquals( maxtoverify + 1, maxtocheck);

	}

	@Test
	public void chapter_by_course() throws SQLException {
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		List<Integer> chapters = new ArrayList<Integer>();
		chapters = coursedao.chapter_by_course(1);

		assertEquals(1,chapters.get(0));
		assertEquals(2,chapters.get(1));
		assertEquals(3,chapters.get(2));

	}

	@Test
	public void course_name() throws SQLException {
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		ImmutableCourse c;
		c = coursedao.course_name(1);
		assertEquals("Computer Essential",c.getName() );

	}
	
	@Test
	public void exam_passed() throws SQLException {
		Chapter_CourseDao coursedao = new Chapter_CourseDao(connection);
		List<ImmutableCourse> c = new ArrayList<ImmutableCourse>();
		c = coursedao.exam_passed(2);
		assertEquals("Computer Essential",c.get(0).getName());

	}

	@Test
	public void subscribe_user() throws SQLException {
		connection.setAutoCommit(false);
		Chapter_CourseDao c = new Chapter_CourseDao(connection);
		User u = new User();
		c.subscribe_user(2, 0, 1);

		String query = "SELECT * from iscrizioni WHERE idCourse = ? and id_User = ? and idChapter = ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, 2);
			pstatement.setInt(2, 0);
			pstatement.setInt(3, 1);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					assertEquals(2,result.getInt("idCourse"));
					assertEquals(0,result.getInt("id_User"));
					assertEquals(1,result.getInt("idChapter"));
					assertEquals(0,result.getInt("passed"));
				}
			}
		}
		connection.rollback();

		connection.setAutoCommit(true);
	}

	@Test
	public void insert_chapteR_NOFINAL() throws SQLException {
		connection.setAutoCommit(false);
		Chapter_CourseDao c = new Chapter_CourseDao(connection);
		Chapter chapter = new Chapter();
		c.insert_chapter(1, 4, "try", "video", 0, "trydescription");

		String query = "SELECT * from chapter WHERE course = ? and chapter = ?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, 1);
			pstatement.setInt(2, 4);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					chapter.setDescription(result.getString("description"));
					chapter.setIdCourse(result.getInt("course"));
					chapter.setIdChapter(result.getInt("chapter"));
					chapter.setName(result.getString("name"));
					chapter.setVideo(result.getString("video"));
				}
			}
		}
		connection.rollback();

		assertEquals("trydescription",chapter.getDescription());
		assertEquals(4, chapter.getIdChapter());
		assertEquals(1, chapter.getIdCourse());
		assertEquals("try",chapter.getName());
		assertEquals("video",chapter.getVideo());
		

		connection.setAutoCommit(true);
	}

	@Test
	public void insert_chapteR_FINAL() throws SQLException {
		connection.setAutoCommit(false);
		// UserDAO userdao = new UserDAO(connection);
		Chapter_CourseDao c = new Chapter_CourseDao(connection);
		Chapter chapter = new Chapter();
		c.insert_chapter(1, 4, "try", null, 1, "trydescription"); 

		String query = "SELECT * from chapter WHERE course = ? and chapter = ?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, 1);
			pstatement.setInt(2, 4);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					chapter.setDescription(result.getString("description"));
					chapter.setIdCourse(result.getInt("course"));
					chapter.setIdChapter(result.getInt("chapter"));
					chapter.setName(result.getString("name"));
					chapter.setVideo(result.getString("video"));
				}
			}
		}
		connection.rollback();

		assertEquals("trydescription",chapter.getDescription());
		assertEquals(4, chapter.getIdChapter());
		assertEquals(1, chapter.getIdCourse());
		assertEquals("try",chapter.getName());
		connection.setAutoCommit(true);
	}

	@Test
	public void insert_course() throws SQLException {
		connection.setAutoCommit(false);
		// UserDAO userdao = new UserDAO(connection);
		Chapter_CourseDao c = new Chapter_CourseDao(connection);
		Course course = new Course();
		c.insert_course(0, "NOME", "DESCRIPTION"); 
		String query = "SELECT * from COURSES WHERE IDCOURSES = ?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, 0); 
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					course.setDescription(result.getString("description"));
					course.setIdCourse(result.getInt("idcourses"));
					course.setName(result.getString("name"));
				}
			}
		}
		connection.rollback();

		assertEquals( "DESCRIPTION", course.getDescription());
		assertEquals(0,course.getIdCourse());
		assertEquals("NOME", course.getName());


		connection.setAutoCommit(true);
	}

}
