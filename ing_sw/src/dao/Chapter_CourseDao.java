package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.midi.Soundbank;

import beans.Chapter;
import beans.Course;
import immutablebeans.ImmutableChapter;
import immutablebeans.ImmutableCourse;

/**
 * @author loren
 *
 */
public class Chapter_CourseDao {
	private Connection connection;

	public Chapter_CourseDao(Connection connection) {
		this.connection = connection;
	}

	/**
	 * search for the list of courses followed by a user
	 * 
	 * @param id_user
	 * @return list of course followed by the user
	 * @throws SQLException
	 */
	public List<ImmutableCourse> Course_by_id(int id_user) throws SQLException {
		List<ImmutableCourse> courses = new ArrayList<ImmutableCourse>();
		String query = "SELECT DISTINCT idcourses,name,description FROM courses join iscrizioni ON iscrizioni.idCourse = courses.idcourses where id_User = ? ";

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id_user);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Course course = new Course();
					course.setIdCourse(result.getInt("idcourses"));
					course.setName(result.getString("name"));
					course.setDescription(result.getString("description"));
					courses.add(course);
				}
			}
		}
		return courses;
	}

	/**
	 * search for the list of courses not followed by a user
	 * 
	 * @param id_user
	 * @return list of course followed by the user
	 * @throws SQLException
	 */
	public List<ImmutableCourse> Course_by_id_notin(int id_user) throws SQLException {
		List<ImmutableCourse> courses = new ArrayList<ImmutableCourse>();
		String query = "SELECT * FROM courses where idcourses NOT IN (select distinct idCourse from iscrizioni where id_User = ?)";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id_user);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Course course = new Course();
					course.setIdCourse(result.getInt("idcourses"));
					course.setName(result.getString("name"));
					course.setDescription(result.getString("description"));
					courses.add(course);
				}
			}
		}
		return courses;
	}

	/**
	 * 
	 * 
	 * @return all the chapter
	 * @throws SQLException
	 */
	public List<ImmutableChapter> allchapter() throws SQLException {
		List<ImmutableChapter> chapters = new ArrayList<ImmutableChapter>();

		String query = "SELECT * from chapter";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Chapter chapter = new Chapter();
					chapter.setIdChapter(result.getInt("chapter"));
					chapter.setIdCourse(result.getInt("course"));
					chapter.setName(result.getString("name"));
					chapter.setVideo(result.getString("video"));
					chapters.add(chapter);
				}
			}
		}
		return chapters;
	}

	/**
	 * 
	 * @param id
	 * @return all the chapter of a course
	 * @throws SQLException
	 */
	public List<ImmutableChapter> chapterby(int id) throws SQLException {
		List<ImmutableChapter> chapters = new ArrayList<ImmutableChapter>();

		String query = "SELECT * from chapter WHERE course = ?";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id);

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Chapter chapter = new Chapter();
					chapter.setIdChapter(result.getInt("chapter"));
					chapter.setIdCourse(result.getInt("course"));
					chapter.setName(result.getString("name"));
					chapter.setVideo(result.getString("video"));
					chapter.setDescription(result.getString("description"));
					chapters.add(chapter);
				}
			}
		}
		return chapters;
	}

	/**
	 * 
	 * @return the maximum id of the courses
	 * @throws SQLException
	 */
	public int maxCourse() throws SQLException {
		String query = "SELECT MAX(idcourses) as idcourses from courses";
		int i = 0;
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					i = result.getInt("idcourses") + 1;
				}
			}
		}
		return i; 
	}

	/**
	 * 
	 * @param id_course
	 * @return the maximum id of chapter in a course
	 * @throws SQLException
	 */
	public int maxchapterby_id(int id_course) throws SQLException {
		String query = "SELECT MAX(chapter) as idchapter from chapter where course = ?";
		int i = 0;
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id_course);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					i = result.getInt("idchapter");
				}
			}
		}

		i++;
		return i;
	}

	/**
	 * 
	 * @param id
	 * @return lista di id chapter di un corso 
	 * @throws SQLException
	 */
	public List<Integer> chapter_by_course(int id) throws SQLException {
		List<Integer> chapters = new ArrayList<Integer>();
		String query = "SELECT * from chapter WHERE course = ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id);

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					int chapter = result.getInt("chapter");
					chapters.add(chapter);
				}
			}
		}
		return chapters;
	}

	/**
	 * 
	 * @param id
	 * @return the name of a courses called by the id
	 * @throws SQLException
	 */
	public ImmutableCourse course_name(int id) throws SQLException {
		Course c = new Course();
		String query = "SELECT * from courses WHERE idcourses = ? ";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id);

			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					c.setName(result.getString("name"));
				}
			}
		}
		return c;
	}

	/**
	 * subscribe a user in a chapter of a course
	 * 
	 * @param id_course
	 * @param id_user
	 * @param id_chapter
	 * @throws SQLException
	 */
	public void subscribe_user(int id_course, int id_user, int id_chapter) throws SQLException {
		String query = "INSERT INTO iscrizioni (idCourse, id_User, idChapter, passed) VALUES (?,?,?,0);\n";

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id_course);
			pstatement.setInt(2, id_user);
			pstatement.setInt(3, id_chapter);
			pstatement.executeUpdate();
		}
	}

	/**
	 * insert a chapter in a course
	 * 
	 * @param idcourse
	 * @param idchapter
	 * @param name
	 * @param video
	 * @param fin
	 * @param description
	 * @throws SQLException
	 */
	public void insert_chapter(int idcourse, int idchapter, String name, String video, int fin, String description)
			throws SQLException {
		String query = "INSERT INTO chapter (course, chapter, name, video, is_final, description) VALUES (?,?,?,?,0,?)";
		String query2 = "INSERT INTO chapter (course, chapter, name, is_final,description) VALUES (?,?,?,1,?)";

		if (fin == 0) {
			try (PreparedStatement pstatement = connection.prepareStatement(query);) {
				pstatement.setInt(1, idcourse);
				pstatement.setInt(2, idchapter);
				pstatement.setString(3, name);
				pstatement.setString(4, video);
				pstatement.setString(5, description);
				pstatement.executeUpdate();
			}
		} else {
			try (PreparedStatement pstatement = connection.prepareStatement(query2);) {
				pstatement.setInt(1, idcourse);
				pstatement.setInt(2, idchapter);
				pstatement.setString(3, name);
				pstatement.setString(4, description);
				pstatement.executeUpdate();
			}
		}

	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param description
	 * @throws SQLException
	 */
	public void insert_course(int id, String name, String description) throws SQLException {
		String query = "insert into courses(idcourses, name, description) values (?,?,?) ";

		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id);
			pstatement.setString(2, name);
			pstatement.setString(3, description);
			pstatement.executeUpdate();
		}
	}

	/**
	 * return a list of quiz passed to verify
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<ImmutableCourse> exam_passed(int id_user) throws SQLException {
		List<ImmutableCourse> courses_passed = new ArrayList<ImmutableCourse>();

		String query = "SELECT courses.name FROM db_videocorsi.iscrizioni join chapter on chapter.course = iscrizioni.idCourse and chapter.chapter = iscrizioni.idChapter join courses on courses.idcourses = chapter.course and courses.idCourses = iscrizioni.idCourse where is_final = 1 and passed = 2 and id_User = ?;";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			pstatement.setInt(1, id_user);
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					Course course = new Course();
					course.setName(result.getString("name"));

					courses_passed.add(course);
				}
			}
		}
		return courses_passed;
	}

}
