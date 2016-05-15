package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import attendanceapp.util.AttendanceAppUtils;

public class SchoolCreateRequestObject {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	private long id;

	@NotBlank()
	@Pattern(regexp = AttendanceAppUtils.USERNAME_REGEX)
	@JsonProperty(value = "username")
	private String username;

	@NotBlank()
	@Pattern(regexp = AttendanceAppUtils.PASSWORD_REGEX)
	@JsonProperty(value = "password")
	private String password;

	@NotBlank()
	@Length(max = 250)
	@JsonProperty(value = "name")
	private String name;

	@NotBlank()
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.TELEPHONE_VALIDATOR_REGEX)
	@JsonProperty(value = "telephone")
	private String telephone;

	@NotBlank()
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.EMAIL_VALIDATOR_REGEX)
	@JsonProperty(value = "email")
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
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(email).append(id)
				.append(name).append(password).append(telephone).append(username).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		SchoolCreateRequestObject other = (SchoolCreateRequestObject) obj;
		return new EqualsBuilder().append(id, other.getId()).append(email, other.getEmail())
				.append(name, other.getName()).append(password, other.getPassword())
				.append(telephone, other.getTelephone()).append(username, other.getUsername()).isEquals();
	}
}
