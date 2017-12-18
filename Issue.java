import java.io.*;

public class Issue implements Serializable {

	private int number;
	private String username;
	private String subject;
	private String details;
	private Developer assigned;
	private String status;

	public Issue(int number, String username, String status,
                     Developer assigned, String subject, String details) {
		setNumber(number);
		setUsername(username);
                setStatus(status);
                setAssigned(assigned);
		setSubject(subject);
		setDetails(details);
	}

	public int getNumber() {
		return number;
	}

	public String getUsername() {
		return username;
	}

	public String getSubject() {
		return subject;
	}

	public String getDetails() {
		return details;
	}

	public Developer getAssigned() {
		return assigned;
	}

	public String getStatus() {
		return status;
	}
        
	public void setNumber(int number) {
		this.number = number;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setAssigned(Developer assigned) {
		this.assigned = assigned;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String toString() {
                if (status.equals("Assigned") || status.equals("Failed") || status.equals("Opened") || status.equals("Resolved")) {
                        return "# " + String.format("%03d", number) + "   \uc6c3 " + username + space() + " Status: " + status +
                               " (" + assigned.getUsername() + ")" + " - " + subject;
                } else {
                        return "# " + String.format("%03d", number) + "   \uc6c3 " + username + space() + " Status: " + status +
                               " - " + subject;
                }
	}
        
        private String space() {
                int numberOfSpaces = 10 - username.length();
                String space = "";
                
                for (int i = 0; i < numberOfSpaces; i++) {
                        space += " ";
                }
                return space;
        }
}