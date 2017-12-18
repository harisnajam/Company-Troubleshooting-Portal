import java.io.*;

public class Manager implements Serializable {

	private String username;
	private String password;

	public Manager(String username, String password) {
		setUsername(username);
		setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

        @Override
	public String toString() {
		return "[Username: " + username + "    Password: " + password + "]";
	}
}