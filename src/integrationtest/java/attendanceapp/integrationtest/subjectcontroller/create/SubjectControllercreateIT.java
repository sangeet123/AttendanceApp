package attendanceapp.integrationtest.subjectcontroller.create;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.SubjectControllerUtilIT;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;

public class SubjectControllercreateIT extends TestConfigurerIT {

	private static boolean hasBeenSet = false;

	@Before()
	@Override()
	public void setUp() {
		super.setUp();
		if (!hasBeenSet) {
			try {
				AttendanceAppUtilIT
						.mysqlScriptRunner(SubjectControllerUtilIT.INSERT_SUBJECT_QUERY_SQL_SCRIPT_FILE_PATH);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			hasBeenSet = true;
		}
	}

	@AfterClass()
	public static void tearDown() throws Exception {
		AttendanceAppUtilIT.mysqlScriptRunner(SubjectControllerUtilIT.CLEAR_SUBJECT_QUERY_SQL_SCRIPT_FILE_PATH);
	}

	@Test()
	public void create_valid_subject() throws Exception {
		final long schoolId = 1L;
		SubjectCreateRequestObject createObject = SubjectControllerUtilIT
				.getSchoolRequestObject("Computer Architecture", "CA 606", 5);
		getMockMvc()
				.perform(post(SubjectControllerUtilIT.CREATESUBJECT, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(createObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Computer Architecture")))
				.andExpect(jsonPath("$.shortName", is("CA 606"))).andExpect(jsonPath("$.credit", is(5)));
	}

	@Test()
	public void create_subject_with_short_name_that_exist_in_database() throws Exception {
		final long schoolId = 1L;
		final String responseJsonString = "{\"statusCode\":3,\"messages\":[\"Subject with given short name already exists. Please enter different short name.\"]}";
		SubjectCreateRequestObject createObject = SubjectControllerUtilIT
				.getSchoolRequestObject("Computer Architecture", "CA 606", 5);
		getMockMvc()
				.perform(post(SubjectControllerUtilIT.CREATESUBJECT, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(createObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

}
