package attendanceapp.model.responseobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SchoolResponseObject extends CreatedAndUpdatedDate {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "telephone")
	private String telephone;

	@JsonProperty(value = "email")
	private String email;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(createdOn)
				.append(updatedOn).append(email).append(id).append(name).append(telephone).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		SchoolResponseObject other = (SchoolResponseObject) obj;
		return new EqualsBuilder().append(createdOn, other.getCreatedOn()).append(updatedOn, other.getUpdatedOn())
				.append(email, other.getEmail()).append(id, other.getId()).append(name, other.getName())
				.append(telephone, other.getTelephone()).isEquals();
	}

	@Override()
	public String toString() {
		return "SchoolResponseObject [id=" + id + ", name=" + name + ", telephone=" + telephone + ", email=" + email
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

}
