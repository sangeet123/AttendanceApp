package attendanceapp.exceptions;

import attendanceapp.constants.SubjectRestControllerConstants;

public final class SubjectNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8574687058773153047L;

	public SubjectNotFoundException() {
		super(SubjectRestControllerConstants.SUBJECT_DOES_NOT_EXIST);
	}

	public SubjectNotFoundException(String mesg) {
		super(mesg);
	}
}
