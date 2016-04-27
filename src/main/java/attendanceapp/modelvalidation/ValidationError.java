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

}
