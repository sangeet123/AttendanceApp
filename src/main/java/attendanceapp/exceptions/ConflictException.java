package attendanceapp.exceptions;

import java.util.Set;

public final class ConflictException extends RuntimeException {

	private Set<String> fields;

	private static final long serialVersionUID = -1577450877995620835L;

	public ConflictException(String mesg) {
		super(mesg);
	}

	public ConflictException(Set<String> fields) {
		this.fields = fields;
	}

	public Set<String> getFields() {
		return fields;
	}

}
