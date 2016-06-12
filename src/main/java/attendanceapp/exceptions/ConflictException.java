package attendanceapp.exceptions;

public final class ConflictException extends RuntimeException {

	private static final long serialVersionUID = -1577450877995620835L;

	public ConflictException(String mesg) {
		super(mesg);
	}

}
