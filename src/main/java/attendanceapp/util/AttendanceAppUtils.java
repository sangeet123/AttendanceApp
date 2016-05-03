package attendanceapp.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

public final class AttendanceAppUtils {

	public AttendanceAppUtils() {
		throw new Error();
	}

	public static final String EXCEPTION_MESSAGE_DELIMITER = ",";
	public static final String EMAIL_VALIDATOR_REGEX = "^(.+)@(.+)$";
	public static final String TELEPHONE_VALIDATOR_REGEX = "^(?:(?:\\+?1\\s*(?:[.-]\\s*)?)?(?:\\(\\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\\s*\\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\\s*(?:[.-]\\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\\s*(?:[.-]\\s*)?([0-9]{4})(?:\\s*(?:#|x\\.?|ext\\.?|extension)\\s*(\\d+))?$";
	public static final String USERNAME_REGEX = "^[a-z0-9_-]{3,15}$";
	public static final String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	public static final String COMMA_SEPERATED_IDS_REGEX = "^[0-9]+(,[0-9]+)*$";
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static List<String> messageToList(String mesg) {
		List<String> messages = new ArrayList<>();
		messages.add(mesg);
		return messages;
	}

}
