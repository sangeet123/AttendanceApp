package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import attendanceapp.util.AttendanceAppUtils;

public class StaffCreateRequestObject {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	private long id;

	@NotBlank()
	@Length(max = 250)
	@JsonProperty(value = "firstName")
	private String firstName;

	@NotBlank()
	@Length(max = 250)
	@JsonProperty(value = "lastName")
	private String lastName;

	@NotBlank()
	@Length(max = 250)
	@JsonProperty(value = "shortName")
	private String shortName;

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

	@Length(max = 2500)
	@JsonProperty(value = "comment")
	private String comment;

	@NotBlank()
	@Length(max = 20)
	@JsonProperty(value = "role")
	private String role;

	@NotBlank()
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.USERNAME_REGEX)
	@JsonProperty(value = "username")
	private String username;

	@NotBlank()
	@Length(max = 250)
	@Pattern(regexp = AttendanceAppUtils.PASSWORD_REGEX)
	@JsonProperty(value = "password")
	private String password;

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override()
	public String toString() {
		return "StaffCreateRequestObject [firstName=" + firstName + ", lastName=" + lastName + ", shortName="
				+ shortName + ", telephone=" + telephone + ", email=" + email + ", comment=" + comment + "]";
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(id).append(email)
				.append(firstName).append(lastName).append(shortName).append(telephone).append(comment).append(role)
				.append(username).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		StaffCreateRequestObject other = (StaffCreateRequestObject) obj;
		return new EqualsBuilder().append(email, other.getEmail()).append(firstName, other.getFirstName())
				.append(username, other.getUsername()).append(id, other.getId()).append(lastName, other.getLastName())
				.append(shortName, other.getShortName()).append(role, other.getRole())
				.append(telephone, other.getTelephone()).append(comment, other.getComment()).isEquals();
	}
}
