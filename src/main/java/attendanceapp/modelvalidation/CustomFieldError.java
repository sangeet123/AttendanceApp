package attendanceapp.modelvalidation;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CustomFieldError implements Serializable {

	private static final long serialVersionUID = -2739123279673816477L;

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;
	private final String field;
	private final String message;

	public CustomFieldError(final String field, final String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(message).append(field)
				.toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CustomFieldError other = (CustomFieldError) obj;
		return new EqualsBuilder().append(field, other.getField()).append(message, other.getMessage()).isEquals();
	}

	@Override
	public String toString() {
		return "CustomFieldError [field=" + field + ", message=" + message + "]";
	}
}
