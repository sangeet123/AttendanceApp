package attendanceapp.modelvalidation;

import java.io.Serializable;

public class CustomFieldError implements Serializable {

	private static final long serialVersionUID = -2739123279673816477L;

	private String field;

	private String message;

	public CustomFieldError(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
