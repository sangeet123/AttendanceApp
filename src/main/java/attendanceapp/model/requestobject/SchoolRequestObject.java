package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import attendanceapp.util.AttendanceAppUtils;

public class SchoolRequestObject {

	private long id;

	@NotBlank()
	@Pattern(regexp = AttendanceAppUtils.USERNAME_REGEX)
	private String username;

	@NotBlank()
	@Pattern(regexp = AttendanceAppUtils.PASSWORD_REGEX)
	private String password;

	@NotBlank()
	@Length(max = 250)
	private String name;

	@NotBlank()
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.TELEPHONE_VALIDATOR_REGEX)
	private String telephone;

	@NotBlank()
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.EMAIL_VALIDATOR_REGEX)
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long schoolId) {
		this.id = schoolId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String schoolName) {
		this.name = schoolName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override()
	public String toString() {
		return "StudentRequestObject [id=" + id + ", username=" + username + ", password=" + password + ", schoolName="
				+ name + ", telephone=" + telephone + ", email=" + email + "]";
	}

	@Override()
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((telephone == null) ? 0 : telephone.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchoolRequestObject other = (SchoolRequestObject) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (telephone == null) {
			if (other.telephone != null)
				return false;
		} else if (!telephone.equals(other.telephone))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
