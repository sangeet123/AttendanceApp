package attendanceapp.model.responseobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StaffResponseObject extends CreatedAndUpdatedDate {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	private long id;

	@JsonProperty(value = "firstName")
	private String firstName;

	@JsonProperty(value = "lastName")
	private String lastName;

	@JsonProperty(value = "shortName")
	private String shortName;

	@JsonProperty(value = "telephone")
	private String telephone;

	@JsonProperty(value = "role")
	private String role;

	@JsonProperty(value = "email")
	private String email;

	@JsonProperty(value = "comment")
	private String comment;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override()
	public String toString() {
		return "StaffResponseObject [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", shortName="
				+ shortName + ", telephone=" + telephone + ", role=" + role + ", email=" + email + ", comment="
				+ comment + "]";
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(id).append(email)
				.append(firstName).append(lastName).append(shortName).append(role).append(telephone).append(comment)
				.toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		StaffResponseObject other = (StaffResponseObject) obj;
		return new EqualsBuilder().append(id, other.getId()).append(email, other.getEmail())
				.append(firstName, other.getFirstName()).append(lastName, other.getLastName())
				.append(role, other.getRole()).append(shortName, other.getShortName())
				.append(telephone, other.getTelephone()).append(comment, other.getComment()).isEquals();
	}
}
