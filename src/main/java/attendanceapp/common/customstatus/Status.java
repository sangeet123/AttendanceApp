package attendanceapp.common.customstatus;

import java.util.ArrayList;
import java.util.List;

public abstract class Status {

	private int statusCode;

	private List<String> messages = new ArrayList<>();

	public Status() {

	}

	public Status(List<String> message, int code) {
		this.messages = message;
		this.statusCode = code;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public List<String> getMessage() {
		return messages;
	}

	public void setMessage(List<String> messages) {
		this.messages = messages;
	}

}
