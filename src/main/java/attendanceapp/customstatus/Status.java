package attendanceapp.customstatus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Status {

	private static final int INITIAL_NONZERO_ODD_NUM = 17;
	private static final int MULTIPLIER_NONZERO_ODD_NUM = 157;

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

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	@Override()
	public int hashCode() {
		return new HashCodeBuilder(INITIAL_NONZERO_ODD_NUM, MULTIPLIER_NONZERO_ODD_NUM).append(messages)
				.append(statusCode).toHashCode();
	}

	@Override()
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Status other = (Status) obj;
		return new EqualsBuilder().append(statusCode, other.getStatusCode()).append(messages, other.getMessages())
				.isEquals();
	}

	@Override()
	public String toString() {
		return "Status [statusCode=" + statusCode + ", messages=" + messages + "]";
	}
}
