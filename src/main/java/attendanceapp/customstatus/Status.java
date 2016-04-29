package attendanceapp.customstatus;

import java.util.ArrayList;
import java.util.List;

public class Status {

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

	@Override()
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		result = prime * result + statusCode;
		return result;
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		if (statusCode != other.statusCode)
			return false;
		return true;
	}

	@Override()
	public String toString() {
		return "Status [statusCode=" + statusCode + ", messages=" + messages + "]";
	}
}
