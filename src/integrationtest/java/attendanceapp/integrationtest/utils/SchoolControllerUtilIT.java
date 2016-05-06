package attendanceapp.integrationtest.utils;

import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;

public final class SchoolControllerUtilIT {

	public SchoolControllerUtilIT() {
		throw new Error();
	}

	// URI for school controller	
	public static final String GETSCHOOLWITHID = "/school/{id}";
	public static final String GETALLSCHOOLS = "/school";
	public static final String CREATESCHOOL = "/school/create";
	public static final String DELETESCHOOL = "/school/delete/{id}";
	public static final String UPDATESCHOOL = "/school/update";
	public static final String DELETE_SELECTED_SCHOOL = "/school/deleteselected";

	// Key for school model
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String TELEPHONE = "telephone";
	public static final String DELEIMITER = ":";

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
