package attendanceapp.unittest.utils;

import java.time.Clock;
import java.time.LocalDateTime;

import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;

public final class SubjectControllerUnitTestUtil {

	public SubjectControllerUnitTestUtil() {
		throw new Error();
	}

	// URI for school controller
	public static final String GETSUBJECTWITHID = "/school/{schoolid}/subject/{subjectid}";
	public static final String GETALLSUBJECTS = "/school/{schoolid}/subject/";
	public static final String CREATESUBJECT = "/school/{schoolid}/subject/create";
	public static final String DELETESUBJECT = "/school/{schoolid}/subject/delete/{subjectid}";
	public static final String UPDATESUBJECT = "/school/{schoolid}/subject/update";
	public static final String DELETE_SUBJECTS = "/school/{schoolid}/subject/delete";

	// Key for school model
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String SHORTNAME = "shortname";
	public static final String CREDIT = "credit";
	public static final String DELEIMITER = ":";

	public static SubjectResponseObject getSubjectResponseObject(final long id, final String name,
			final String shortname, final int credit) {
		SubjectResponseObject subjectResponseObject = new SubjectResponseObject();
		subjectResponseObject.setId(id);
		LocalDateTime utcNow = LocalDateTime.now(Clock.systemUTC());
		subjectResponseObject.setUpdatedOn(utcNow);
		subjectResponseObject.setCreatedOn(utcNow);
		subjectResponseObject.setShortName(shortname);
		subjectResponseObject.setName(name);
		return subjectResponseObject;
	}

	public static SubjectCreateRequestObject getSubjectCreateRequestObject(final long id, final String name,
			final String shortname, final int credit) {
		SubjectCreateRequestObject subjectCreateRequestObject = new SubjectCreateRequestObject();
		subjectCreateRequestObject.setId(id);
		subjectCreateRequestObject.setShortName(shortname);
		subjectCreateRequestObject.setName(name);
		return subjectCreateRequestObject;
	}

	public static SubjectUpdateRequestObject getSubjectUpdateRequestObject(final long id, final String name,
			final String shortname, final int credit) {
		SubjectUpdateRequestObject subjectUpdateRequestObject = new SubjectUpdateRequestObject();
		subjectUpdateRequestObject.setId(id);
		subjectUpdateRequestObject.setShortName(shortname);
		subjectUpdateRequestObject.setName(name);
		return subjectUpdateRequestObject;

	}

}
