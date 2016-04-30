package attendanceapp.constants;

public final class SchoolRestControllerConstants {

	public SchoolRestControllerConstants() {
		throw new Error();
	}

	// URI Constants
	public static final String ROOT = "/school";
	public static final String GET_SCHOOL = "/{id}";
	public static final String GET_SCHOOL_LIST = "";
	public static final String DELETE_SCHOOL = "/delete/{id}";
	public static final String DELETE_SCHOOL_LIST = "/deleteall/";
	public static final String CREATE_SCHOOL = "/create";
	public static final String UPDATE_SCHOOL = "/update";

	// REST Operation message Constants
	public static final String DELETE_SUCCESS = "School has been deleted successfully.";
	public static final String DELETE_FAILURE = "School could not be deleted.";
	public static final String SCHOOL_DOES_NOT_EXIST = "School does not exist.";
	public static final String CREATE_SUCCESS = "School created sucessfully.";
	public static final String CREATE_USER_FAILURE_DUPLICATE_ENTRY = "User with given name already exists. Please try with different name.";
	public static final String DUPLICATE_SCHOOL_NAME = "School with given name already exists. Please enter different name.";

	// validation constants
	public static final String SCHOOL_NAME_REQUIRED = "School name cannot be empty.";
	public static final String SCHOOL_TELEPHONE_REQUIRED = "School telephone cannot be empty";
	public static final String SCHOOL_TELEPHONE_INVALID = "Invalid telephone no. Please enter the valid telephone no";
	public static final String SCHOOL_EMAIL_REQUIRED = "School email is required";
	public static final String SCHOOL_NAME_LENGTH_CANNOT_EXCEED_250 = "School name length should be less than 250 characters";
	public static final String SCHOOL_EMAIL_INVALID = "School email is not a valid email. Please enter the valid email address";

}
