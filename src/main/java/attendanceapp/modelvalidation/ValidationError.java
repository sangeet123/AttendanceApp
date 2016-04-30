package attendanceapp.modelvalidation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError implements Serializable {

	private static final long serialVersionUID = -18202280331077017L;

	private List<CustomFieldError> fieldErrors = new ArrayList<>();

	public ValidationError() {

	}

	public List<CustomFieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<CustomFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public void addFieldError(String path, String message) {
		CustomFieldError error = new CustomFieldError(path, message);
		fieldErrors.add(error);
	}

	@Override()
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldErrors == null) ? 0 : fieldErrors.hashCode());
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
		ValidationError other = (ValidationError) obj;
		if (fieldErrors == null) {
			if (other.fieldErrors != null)
				return false;
		} else if (!fieldErrors.equals(other.fieldErrors))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ValidationError [fieldErrors=" + fieldErrors + "]";
	}

}
