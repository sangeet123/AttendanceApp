package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import attendanceapp.util.AttendanceAppUtils;

public class SchoolRequestObject {

	private long id;

	@NotBlank
	@Pattern(regexp = AttendanceAppUtils.USERNAME_REGEX)
	private String username;

	@NotBlank
	@Pattern(regexp = AttendanceAppUtils.PASSWORD_REGEX)
	private String password;

	@NotBlank
	@Length(max = 250)
	private String name;

	@NotBlank
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.TELEPHONE_VALIDATOR_REGEX)
	private String telephone;

	@NotBlank
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

	@Override
	public String toString() {
		return "StudentRequestObject [id=" + id + ", username=" + username
				+ ", password=" + password + ", schoolName=" + name
				+ ", telephone=" + telephone + ", email=" + email + "]";
	}

}
