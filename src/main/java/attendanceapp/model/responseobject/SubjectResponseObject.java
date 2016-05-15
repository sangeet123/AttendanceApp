package attendanceapp.model.responseobject;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubjectResponseObject extends CreatedAndUpdatedDate {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value = "name")
	private String name;

	@JsonProperty(value = "shortName")
	private String shortName;

	@JsonProperty(value = "credit")
	private int credit;

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(createdOn)
				.append(updatedOn).append(credit).append(id).append(name).append(shortName).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		SubjectResponseObject other = (SubjectResponseObject) obj;
		return new EqualsBuilder().append(createdOn, other.getCreatedOn()).append(updatedOn, other.getUpdatedOn())
				.append(credit, other.getCredit()).append(id, other.getId()).append(name, other.getName())
				.append(shortName, other.getShortName()).isEquals();
	}

	@Override()
	public String toString() {
		return "SubjectResponseObject [id=" + id + ", name=" + name + ", shortName=" + shortName + ", credit=" + credit
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + "]";
	}

}
