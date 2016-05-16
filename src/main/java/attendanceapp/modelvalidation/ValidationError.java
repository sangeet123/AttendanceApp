package attendanceapp.modelvalidation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ValidationError implements Serializable {

	private static final long serialVersionUID = -18202280331077017L;

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;
	private final List<CustomFieldError> fieldErrors = new ArrayList<>();

	public List<CustomFieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void addFieldError(final String path, final String message) {
		CustomFieldError error = new CustomFieldError(path, message);
		fieldErrors.add(error);
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(fieldErrors)
				.toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ValidationError other = (ValidationError) obj;
		return new EqualsBuilder().append(fieldErrors, other.getFieldErrors()).isEquals();
	}

	@Override
	public String toString() {
		return "ValidationError [fieldErrors=" + fieldErrors + "]";
	}

}
