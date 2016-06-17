package attendanceapp.integrationtest.schoolcontroller.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.SchoolControllerUtilIT;

public class SchoolControllerdeleteIT extends TestConfigurerIT {

	private static boolean hasBeenSet = false;

	@Before()
	@Override()
	public void setUp() {
		super.setUp();
		if (!hasBeenSet) {
			try {
				AttendanceAppUtilIT.mysqlScriptRunner(SchoolControllerUtilIT.INSERT_SCHOOL_QUERY_SQL_SCRIPT_FILE_PATH);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			hasBeenSet = true;
		}
	}

	@AfterClass()
	public static void tearDown() {
		try {
			AttendanceAppUtilIT.mysqlScriptRunner(SchoolControllerUtilIT.CLEAR_SCHOOL_QUERY_SQL_SCRIPT_FILE_PATH);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test()
	public void delete_school_with_valid_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"School has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, validSchoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_valid_id_10_that_exist() throws Exception {
		final long validSchoolId = 10L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"School has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, validSchoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_valid_id_15_that_does_not_exist() throws Exception {
		final long validIdThatDoesNotExist = 15L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, validIdThatDoesNotExist)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));

	}

	@Test()
	public void delete_school_with_invalid_id_negative_1() throws Exception {
		final long invalidId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, invalidId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_invalid_id_one() throws Exception {
		final String invalidStringId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, invalidStringId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));

	}
}
