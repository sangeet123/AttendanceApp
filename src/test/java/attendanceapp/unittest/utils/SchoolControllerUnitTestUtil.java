package attendanceapp.unittest.utils;

import java.time.Clock;
import java.time.LocalDateTime;

import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public final class SchoolControllerUnitTestUtil {

	public SchoolControllerUnitTestUtil() {
		throw new Error();
	}

	// URI for school controller
	public static final String GETSCHOOLWITHID = "/admin/school/{id}";
	public static final String GETALLSCHOOLS = "/admin/school";
	public static final String CREATESCHOOL = "/admin/school/create";
	public static final String DELETESCHOOL = "/admin/school/delete/{id}";
	public static final String UPDATESCHOOL = "/admin/school/update";
	public static final String DELETE_SCHOOLS = "/admin/school/delete";

	// Key for school model
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String TELEPHONE = "telephone";
	public static final String DELEIMITER = ":";

	public static SchoolResponseObject getSchoolResponseObject(long id, String name, String email, String telephone) {
		SchoolResponseObject schoolResponseObject = new SchoolResponseObject();
		schoolResponseObject.setId(id);
		schoolResponseObject.setTelephone(telephone);
		schoolResponseObject.setEmail(email);
		schoolResponseObject.setTelephone(telephone);
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		schoolResponseObject.setUpdatedOn(utcNow);
		schoolResponseObject.setCreatedOn(utcNow);
		return schoolResponseObject;
	}

	public static SchoolCreateRequestObject getSchoolCreateRequestObject(String username, String password, String name,
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
