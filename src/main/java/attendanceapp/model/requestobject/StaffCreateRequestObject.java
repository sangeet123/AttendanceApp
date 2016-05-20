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

	@NotBlank()
	@Length(max = 250)
	@JsonProperty(value = "name")
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "StaffCreateRequestObject [" + "name=" + name + ", shortName=" + shortName + ", telephone=" + telephone
				+ ", email=" + email + ", comment=" + comment + "]";
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(email).append(name)
				.append(shortName).append(telephone).append(comment).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		StaffCreateRequestObject other = (StaffCreateRequestObject) obj;
		return new EqualsBuilder().append(email, other.getEmail()).append(name, other.getName())
				.append(shortName, other.getShortName()).append(telephone, other.getTelephone())
				.append(comment, other.getComment()).isEquals();
	}
}
