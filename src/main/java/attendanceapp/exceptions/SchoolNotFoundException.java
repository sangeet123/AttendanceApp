package attendanceapp.exceptions;

import attendanceapp.constants.SchoolRestControllerConstants;

public final class SchoolNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2032039436645325205L;

	public SchoolNotFoundException() {
		super(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST);
	}

	public SchoolNotFoundException(String mesg) {
		super(mesg);
	}
}
