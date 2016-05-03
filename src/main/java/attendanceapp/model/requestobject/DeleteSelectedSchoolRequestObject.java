package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import attendanceapp.util.AttendanceAppUtils;

public class DeleteSelectedSchoolRequestObject {

	@NotBlank()
	@Pattern(regexp = AttendanceAppUtils.COMMA_SEPERATED_IDS_REGEX)
	private String commaSeparatedIds;

	public String getCommaSeparatedIds() {
		return commaSeparatedIds;
	}

	public void setCommaSeparatedIds(String commaSeparatedIds) {
		this.commaSeparatedIds = commaSeparatedIds;
	}

	@Override()
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commaSeparatedIds == null) ? 0 : commaSeparatedIds.hashCode());
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
		DeleteSelectedSchoolRequestObject other = (DeleteSelectedSchoolRequestObject) obj;
		if (commaSeparatedIds == null) {
			if (other.commaSeparatedIds != null)
				return false;
		} else if (!commaSeparatedIds.equals(other.commaSeparatedIds))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeleteSelectedSchoolRequestObject [commaSeparatedIds=" + commaSeparatedIds + "]";
	}

}
