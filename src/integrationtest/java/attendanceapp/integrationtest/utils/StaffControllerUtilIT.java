package attendanceapp.integrationtest.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.codec.Base64;

public final class StaffControllerUtilIT {

	// URI for school controller
	public static final String GETSTAFFWITHID = "/school/{schoolId}/staff/{staffId}";
	public static final String GETALLSTAFFS = "/school/{schoolId}/staff";
	public static final String CREATESTAFF = "/school/{schoolId}/staff/create";
	public static final String DELETESTAFF = "/school/{schoolId}/staff/delete/{staffId}";
	public static final String UPDATESTAFF = "/school/{schoolId}/staff/update";
	public static final String DELETE_STAFFS = "/school/{schoolId}/staff/delete";

	// sql script file for testing staff controller.
	// pre-populate the necessary data
	public static final String INSERT_STAFF_QUERY_SQL_SCRIPT_FILE_PATH = "it-insert-staff-queries.sql";
	public static final String CLEAR_STAFF_QUERY_SQL_SCRIPT_FILE_PATH = "it-delete-staff-queries.sql";

	// password for testing admin
	public static final String BASIC_DIGEST_HEADER_VALUE = "Basic "
			+ new String(Base64.encode(("stafftestuser:password").getBytes()));

	private StaffControllerUtilIT() throws InstantiationException {
		throw new InstantiationException();
	}

	public static Map<String, Object> getStaffRequestObject(final String firstName, final String lastName,
			final String userName, final String password, final String email, final String telephone,
			final String shortName, final String role, final String comment) {
		Map<String, Object> request = new HashMap<>();
		request.put("firstName", firstName);
		request.put("lastName", lastName);
		request.put("username", userName);
		request.put("password", password);
		request.put("email", email);
		request.put("telephone", telephone);
		request.put("shortName", shortName);
		request.put("role", role);
		request.put("comment", comment);
		return request;
	}

	public static Map<String, Object> getStaffUpdateRequestObject(final int id, final String firstName,
			final String lastName, final String email, final String telephone, final String shortName,
			final String role, final String comment) {
		Map<String, Object> request = new HashMap<>();
		request.put("id", id);
		request.put("firstName", firstName);
		request.put("lastName", lastName);
		request.put("email", email);
		request.put("telephone", telephone);
		request.put("shortName", shortName);
		request.put("role", role);
		request.put("comment", comment);
		return request;
	}
}
