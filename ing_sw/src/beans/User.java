package beans;

import immutablebeans.ImmutableUser;

public class User implements ImmutableUser{

	private int id;
	private String username;
	private int role;
	private String name;
	private String Cognome;
	private String Email;
	private String Skillscard;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getname() {
		return name;
	}

	public void setname(String surname) {
		this.name = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getSkillscard() {
		return Skillscard;
	}

	public void setSkillscard(String skillscard) {
		Skillscard = skillscard;
	}

}
