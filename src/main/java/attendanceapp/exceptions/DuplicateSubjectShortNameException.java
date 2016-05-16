package attendanceapp.exceptions;

import attendanceapp.constants.SubjectRestControllerConstants;

public final class DuplicateSubjectShortNameException extends RuntimeException {

	private static final long serialVersionUID = -1577450877995620835L;

	public DuplicateSubjectShortNameException() {
		super(SubjectRestControllerConstants.DUPLICATE_SUBJECT_SHORT_NAME);
	}

	public DuplicateSubjectShortNameException(String mesg) {
		super(mesg);
	}

}
