package attendanceapp.exceptions;

import attendanceapp.constants.SchoolRestControllerConstants;

public final class DuplicateSchoolNameException extends RuntimeException {

	private static final long serialVersionUID = -1577450877995620835L;

	public DuplicateSchoolNameException() {
		super(SchoolRestControllerConstants.CREATE_UPDATE_FAILURE_DUPLICATE_ENTRY);
	}

	public DuplicateSchoolNameException(String mesg) {
		super(mesg);
	}

}
