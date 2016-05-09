package attendanceapp.integrationtest.utils;

import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;

public final class SubjectControllerUtilIT {

	public SubjectControllerUtilIT() {
		throw new Error();
	}

	// URI for school controller
	public static final String GETSUBJECTWITHID = "/school/{schoolId}/subject/{subjectId}";
	public static final String GETALLSUBJECTS = "/school/{schoolId}/subject";
	public static final String CREATESCHOOL = "/school/create";
	public static final String DELETESUBJECT = "/school/{schoolId}/subject/delete/{subjectId}";
	public static final String UPDATESCHOOL = "/school/update";
	public static final String DELETE_SELECTED_SCHOOL = "/school/deleteselected";

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
