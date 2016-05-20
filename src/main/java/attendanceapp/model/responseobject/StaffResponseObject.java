package attendanceapp.model.responseobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class StaffResponseObject extends CreatedAndUpdatedDate {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	private String name;

	private String shortName;

	private String telephone;

	private String email;

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
		StaffResponseObject other = (StaffResponseObject) obj;
		return new EqualsBuilder().append(email, other.getEmail()).append(name, other.getName())
				.append(shortName, other.getShortName()).append(telephone, other.getTelephone())
				.append(comment, other.getComment()).isEquals();
	}
}
