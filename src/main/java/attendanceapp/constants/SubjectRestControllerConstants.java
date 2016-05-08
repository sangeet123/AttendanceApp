package attendanceapp.constants;

public final class SubjectRestControllerConstants {

	public SubjectRestControllerConstants() {
		throw new Error();
	}

	// URI Constants
	public static final String ROOT = "/school/{schoolId}/subject";
	public static final String GET_SUBJECT = "/{subjectId}";
	public static final String GET_SUBJECT_LIST = "";
	public static final String DELETE_SUBJECT = "/delete/{subjectId}";
	public static final String DELETE_SUBJECT_LIST = "/deleteselected";
	public static final String CREATE_SUBJECT = "/create";
	public static final String UPDATE_SUBJECT = "/update";

	// REST Operation message Constants
	public static final String DELETE_SUCCESS = "Subject has been deleted successfully.";
	public static final String SELECTED_SUBJECT_DELETE_SUCCESS = "Selected subjects has been deleted successfully.";
	public static final String DELETE_FAILURE = "Subject could not be deleted.";
	public static final String SUBJECT_DOES_NOT_EXIST = "Subject does not exist.";
	public static final String CREATE_SUCCESS = "School created sucessfully.";

	// validation constants
	public static final String SUBJECT_NAME_REQUIRED = "Subject name cannot be empty.";
	public static final String SCHOOL_SHORTNAME_REQUIRED = "Subject short name cannot be empty";
}
