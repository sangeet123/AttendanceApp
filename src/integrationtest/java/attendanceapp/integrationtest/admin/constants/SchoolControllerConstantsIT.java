package attendanceapp.integrationtest.admin.constants;

public final class SchoolControllerConstantsIT {

	public SchoolControllerConstantsIT() {
		throw new Error();
	}

	// URI for school controller
	public static final String GETSCHOOLWITHID = "/school/{id}";
	public static final String GETALLSCHOOLS = "/school";
	public static final String CREATESCHOOL = "/school/create";
	public static final String DELETESCHOOL = "/school/delete/{id}";

	// Key for school model
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String TELEPHONE = "telephone";
	public static final String DELEIMITER = ":";

}
