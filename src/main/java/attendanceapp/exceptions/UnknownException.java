package attendanceapp.exceptions;

public final class UnknownException extends RuntimeException {

	private static final long serialVersionUID = -6994646591132748422L;

	private static final String DEFAULT_MESSAGE = "Something went wrong. Please try again later.";

	public UnknownException() {
		super(DEFAULT_MESSAGE);
	}

	public UnknownException(String mesg) {
		super(mesg);
	}

}
