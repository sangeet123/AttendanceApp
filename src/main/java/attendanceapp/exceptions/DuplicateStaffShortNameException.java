package attendanceapp.exceptions;

import attendanceapp.constants.StaffRestControllerConstants;

public final class DuplicateStaffShortNameException extends RuntimeException {

	private static final long serialVersionUID = -1577450877995620835L;

	public DuplicateStaffShortNameException() {
		super(StaffRestControllerConstants.DUPLICATE_STAFF_SHORT_NAME);
	}

	public DuplicateStaffShortNameException(String mesg) {
		super(mesg);
	}

}
