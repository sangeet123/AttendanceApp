package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import attendanceapp.util.AttendanceAppUtils;

public class SchoolUpdateRequestObject {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	private long id;

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
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(email).append(id)
				.append(name).append(telephone).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		SchoolUpdateRequestObject other = (SchoolUpdateRequestObject) obj;
		return new EqualsBuilder().append(id, other.getId()).append(email, other.getEmail())
				.append(name, other.getName()).append(telephone, other.getTelephone()).isEquals();
	}

	@Override()
	public String toString() {
		return "SchoolUpdateRequestObject [id=" + id + ", name=" + name + ", telephone=" + telephone + ", email="
				+ email + "]";
	}

}
