package attendanceapp.constants;

public final class SubjectRestControllerConstants {

	// URI Constants
	public static final String ROOT = "school/{schoolId}/subject";
	public static final String GET_SUBJECT = "/{subjectId}";
	public static final String GET_SUBJECT_LIST = "";
	public static final String DELETE_SUBJECT = "/delete/{subjectId}";
	public static final String DELETE_SUBJECT_LIST = "/delete";
	public static final String CREATE_SUBJECT = "/create";
	public static final String UPDATE_SUBJECT = "/update";

	// REST Operation message Constants
	public static final String DELETE_SUCCESS = "Subject has been deleted successfully.";
	public static final String SUBJECTS_DELETE_SUCCESS = "Subjects have been deleted successfully.";
	public static final String DELETE_FAILURE = "Subject could not be deleted.";
	public static final String CREATE_SUCCESS = "School created sucessfully.";
	public static final String DUPLICATE_SUBJECT_SHORT_NAME = "Subject with given short name already exists. Please enter different short name.";

	private SubjectRestControllerConstants() throws InstantiationException {
		throw new InstantiationException();
	}

}
