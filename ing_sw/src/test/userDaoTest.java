package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.sound.midi.Soundbank;

import org.apache.commons.lang.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import beans.Quiz;
import beans.User;
import dao.QuizDAO;
import dao.UserDAO;
import immutablebeans.ImmutableUser;
import utils.*;


public class userDaoTest extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private Connection connection = null;

	public userDaoTest() {
		super();
	}

	public void init() throws ServletException {
	}
	
	 @BeforeEach                                         
	    void setUp() throws ServletException, Exception {
		 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_videocorsi", "root", "Fagianogio!2");
	    }

	@Test
	public void checkcredentialtest_with_1() throws SQLException {
		UserDAO userdao = new UserDAO(connection);
		ImmutableUser u;
		u = userdao.checkCredentials("docente","99240839cd88b26623247acdafd58e7e");
		assertEquals(u.getId(), 0);		
	}
	
	
	
	
	@Test
	public void changePass() throws SQLException, UnsupportedEncodingException {
		connection.setAutoCommit(false);
		UserDAO userdao = new UserDAO(connection);
		String tochange = "cambiopassword";
		byte[] bytesOfMessage = tochange.getBytes("UTF-8");
		String pwd = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			byte[] theMD5digest = md.digest(bytesOfMessage);
			
			StringBuilder sb = new StringBuilder();
	        for (byte b : theMD5digest) {
	            sb.append(String.format("%02x", b));
	        }
	        pwd = sb.toString();
		}
		catch (Exception e) {
			
		}
		
		userdao.changePass(0, pwd);
		String query = "SELECT password from user WHERE id = 0 ";
		try (PreparedStatement pstatement = connection.prepareStatement(query);) {
			try (ResultSet result = pstatement.executeQuery();) {
				while (result.next()) {
					assertEquals(pwd, result.getString("password"));
					
				}
			}
		}
		connection.rollback();
		connection.setAutoCommit(true);
	}
}
