package attendanceapp.exceptions;

import attendanceapp.constants.SchoolRestControllerConstants;

public class DuplicateUserNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7116529165155488455L;

	public DuplicateUserNameException() {
		super(SchoolRestControllerConstants.CREATE_USER_FAILURE_DUPLICATE_ENTRY);
	}

	public DuplicateUserNameException(String mesg) {
		super(mesg);
	}

}
