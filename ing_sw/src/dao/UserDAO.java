package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.management.relation.RelationTypeSupport;

import beans.User;
import immutablebeans.ImmutableQuiz;
import immutablebeans.ImmutableUser;

public class UserDAO {
	private Connection con;

	public UserDAO(Connection connection) {
		this.con = connection;
	}

	public ImmutableUser checkCredentials(String usrn, String pwd) throws SQLException {
		String query = "SELECT  * FROM user  WHERE username = ? AND password =?";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, usrn);
			pstatement.setString(2, pwd);
			try (ResultSet result = pstatement.executeQuery();) {
				if (!result.isBeforeFirst())
					return null;
				else { 
					result.next();
					User user = new User();
					user.setId(result.getInt("id"));
					user.setUsername(result.getString("username"));
					user.setRole(result.getInt("role"));
					user.setname(result.getString("nome"));
					user.setCognome(result.getString("cognome"));
					user.setEmail(result.getString("email"));
					user.setSkillscard(result.getString("skillscard"));
					return user;
				}
			}
		}
	}
	
	/**
	 * change the password of a specific user
	 * @param id
	 * @param psw
	 * @throws SQLException
	 */
	public void changePass(int id, String psw) throws SQLException {
		String query = "UPDATE user SET password = ? where id = ? ;";
		try (PreparedStatement pstatement = con.prepareStatement(query);) {
			pstatement.setString(1, psw);
			pstatement.setInt(2, id);
			pstatement.executeUpdate();

		}

	}
	
	
}
