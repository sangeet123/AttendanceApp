package attendanceapp.model.requestobject;

import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import attendanceapp.util.AttendanceAppUtils;

public class DeleteSubjectsRequestObject {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

	@NotBlank()
	@Pattern(regexp = AttendanceAppUtils.COMMA_SEPERATED_IDS_REGEX)
	@JsonProperty(value = "commaSeparatedIds")
	private String commaSeparatedIds;

	public String getCommaSeparatedIds() {
		return commaSeparatedIds;
	}

	public void setCommaSeparatedIds(String commaSeparatedIds) {
		this.commaSeparatedIds = commaSeparatedIds;
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(commaSeparatedIds)
				.toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		DeleteSubjectsRequestObject other = (DeleteSubjectsRequestObject) obj;
		return new EqualsBuilder().append(commaSeparatedIds, other.getCommaSeparatedIds()).isEquals();
	}

	@Override()
	public String toString() {
		return "DeleteSelectedSubjectRequestObject [commaSeparatedIds=" + commaSeparatedIds + "]";
	}

}
