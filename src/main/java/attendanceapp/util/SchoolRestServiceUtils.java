package attendanceapp.util;

import java.util.Arrays;
import java.util.List;

import attendanceapp.customstatus.Status;
import attendanceapp.model.Authority;
import attendanceapp.model.School;
import attendanceapp.model.User;
import attendanceapp.model.requestobject.SchoolRequestObject;
import attendanceapp.requestobjecttomodelmapper.SchoolRequestToAuthoritiesMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolRequestToSchoolMapper;
import attendanceapp.requestobjecttomodelmapper.SchoolRequestToUserMapper;

public class SchoolRestServiceUtils {

	private static final String SPLITTER = "\\s*"
			+ AttendanceAppUtils.EXCEPTION_MESSAGE_DELIMITER + "\\s*";

	public static Status createStatus(String mesg, int code) {
		return new Status(getMessages(mesg), code);
	}

	public static List<String> getMessages(String string) {
		List<String> messages = Arrays.asList(string.split(SPLITTER));
		return messages;
	}

	public static School createSchoolFromSchoolResponseObject(
			final SchoolRequestObject schoolRequestObject) {
		return new SchoolRequestToSchoolMapper.SchoolBuilder()
				.id(schoolRequestObject.getId())
				.email(schoolRequestObject.getEmail())
				.name(schoolRequestObject.getName())
				.telephone(schoolRequestObject.getTelephone()).build();
	}

	public static User createUserFromSchoolResponseObject(
			final SchoolRequestObject schoolRequestObject) {
		return new SchoolRequestToUserMapper.UserBuilder()
				.username(schoolRequestObject.getUsername())
				.password(schoolRequestObject.getPassword()).enabled(true)
				.build();
	}

	public static Authority createAuthorityFromSchoolRequestObject(
			final SchoolRequestObject schoolRequestObject) {
		String authority = Role.ROLE_SCHOOL_ADMIN;
		return new SchoolRequestToAuthoritiesMapper.AuthorityBuilder()
				.username(schoolRequestObject.getUsername())
				.authority(authority).build();
	}

}
