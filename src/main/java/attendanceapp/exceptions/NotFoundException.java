package attendanceapp.exceptions;

public final class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2032039436645325205L;

	public NotFoundException(String mesg) {
		super(mesg);
	}
}
