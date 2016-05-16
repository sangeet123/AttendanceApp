package attendanceapp.integrationtest.utils;

import org.springframework.security.crypto.codec.Base64;

import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;

public final class SubjectControllerUtilIT {

	// URI for school controller
	public static final String GETSUBJECTWITHID = "/school/{schoolId}/subject/{subjectId}";
	public static final String GETALLSUBJECTS = "/school/{schoolId}/subject";
	public static final String CREATESUBJECT = "/school/{schoolId}/subject/create";
	public static final String DELETESUBJECT = "/school/{schoolId}/subject/delete/{subjectId}";
	public static final String UPDATESCHOOL = "/school/update";
	public static final String DELETE_SUBJECTS = "/school/{schoolId}/subject/delete";

	// sql script file for testing school controller.
	// pre-populate the necessary data
	public static final String INSERT_SUBJECT_QUERY_SQL_SCRIPT_FILE_PATH = "it-insert-subject-queries.sql";
	public static final String CLEAR_SUBJECT_QUERY_SQL_SCRIPT_FILE_PATH = "it-delete-subject-queries.sql";

	// password for testing admin
	public static final String BASIC_DIGEST_HEADER_VALUE = "Basic "
			+ new String(Base64.encode(("subjecttestuser:password").getBytes()));

	private SubjectControllerUtilIT() throws InstantiationException {
		throw new InstantiationException();
	}

	public static SubjectCreateRequestObject getSchoolRequestObject(final String name, final String shortName,
			final int credit) {
		SubjectCreateRequestObject createObject = new SubjectCreateRequestObject();
		createObject.setName(name);
		createObject.setCredit(credit);
		createObject.setShortName(shortName);
		return createObject;
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
