package attendanceapp.integrationtest.subjectcontroller.get.id;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

public class SubjectControllergetIdIT extends TestConfigurerIT {

	public static boolean hasBeenSet = false;

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
	public static void tearDown() {
		try {
			AttendanceAppUtilIT.mysqlScriptRunner(SubjectControllerUtilIT.CLEAR_SUBJECT_QUERY_SQL_SCRIPT_FILE_PATH);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test()
	public void get_subject_with_valid_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final long validSubjectId = 1L;
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, validSchoolId, validSubjectId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Computer Programming")))
				.andExpect(jsonPath("$.shortName", is("CP101"))).andExpect(jsonPath("$.credit", is(5)));
	}

	@Test()
	public void get_subject_with_valid_id_2_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final long validSubjectId = 2L;
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, validSchoolId, validSubjectId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(2))).andExpect(jsonPath("$.name", is("Java Programming")))
				.andExpect(jsonPath("$.shortName", is("JP301"))).andExpect(jsonPath("$.credit", is(5)));
	}

	@Test()
	public void get_subject_with_valid_school_id_1_and_invalid_subjectId_10_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final long invalidSubjectId = 10L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Subject does not exist.\"]}";
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, validSchoolId, invalidSubjectId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_subject_with_valid_school_id_1_and_invalid_subjectId_negative_1_that_does_not_exist()
			throws Exception {
		final long validSchoolId = 1L;
		final long invalidSubjectId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Subject does not exist.\"]}";
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, validSchoolId, invalidSubjectId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_subject_with_valid_school_id_1_and_invalid_subjectId_one_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final String invalidSubjectId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, validSchoolId, invalidSubjectId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}
}
