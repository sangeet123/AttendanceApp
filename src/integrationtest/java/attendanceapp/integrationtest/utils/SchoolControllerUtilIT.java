package attendanceapp.integrationtest.utils;

import org.springframework.security.crypto.codec.Base64;

import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;

public final class SchoolControllerUtilIT {

	// sql script file for testing school controller.
	// pre-populate the necessary data
	public static final String INSERT_SCHOOL_QUERY_SQL_SCRIPT_FILE_PATH = "it-insert-school-queries.sql";
	public static final String CLEAR_SCHOOL_QUERY_SQL_SCRIPT_FILE_PATH = "it-delete-school-queries.sql";

	// password for testing admin
	public static final String BASIC_DIGEST_HEADER_VALUE = "Basic "
			+ new String(Base64.encode(("admin:password").getBytes()));

	// URI for school controller
	public static final String GETSCHOOLWITHID = "/admin/school/{id}";
	public static final String GETALLSCHOOLS = "/admin/school";
	public static final String CREATESCHOOL = "/admin/school/create";
	public static final String DELETESCHOOL = "/admin/school/delete/{id}";
	public static final String UPDATESCHOOL = "/admin/school/update";
	public static final String DELETE_SELECTED_SCHOOL = "/admin/school/delete";

	private SchoolControllerUtilIT() throws InstantiationException {
		throw new InstantiationException();
	}

	public static SchoolCreateRequestObject getSchoolRequestObject(String username, String password, String name,
			String email, String telephone) {
		SchoolCreateRequestObject schoolRequestObject = new SchoolCreateRequestObject();
		schoolRequestObject.setUsername(username);
		schoolRequestObject.setPassword(password);
		schoolRequestObject.setName(name);
		schoolRequestObject.setEmail(email);
		schoolRequestObject.setTelephone(telephone);
		return schoolRequestObject;
	}

	public static SchoolUpdateRequestObject getSchoolUpdateRequestObject(long id, String name, String email,
			String telephone) {
		SchoolUpdateRequestObject schoolUpdateRequestObject = new SchoolUpdateRequestObject();
		schoolUpdateRequestObject.setId(id);
		schoolUpdateRequestObject.setName(name);
		schoolUpdateRequestObject.setEmail(email);
		schoolUpdateRequestObject.setTelephone(telephone);
		return schoolUpdateRequestObject;
	}

}
