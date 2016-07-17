package attendanceapp.integrationtest.staffcontroller.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.StaffControllerUtilIT;

public class StaffControllerdeleteIdIT extends TestConfigurerIT {
	private static boolean hasBeenSet = false;

	@Before()
	@Override()
	public void setUp() {
		super.setUp();
		if (!hasBeenSet) {
			try {
				AttendanceAppUtilIT.mysqlScriptRunner(StaffControllerUtilIT.INSERT_STAFF_QUERY_SQL_SCRIPT_FILE_PATH);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			hasBeenSet = true;
		}
	}

	@AfterClass()
	public static void tearDown() {
		try {
			AttendanceAppUtilIT.mysqlScriptRunner(StaffControllerUtilIT.CLEAR_STAFF_QUERY_SQL_SCRIPT_FILE_PATH);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test()
	public void delete_school_with_valid_id_1_and_valid_staff_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final long validStaffId = 1L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Staff has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(StaffControllerUtilIT.DELETESTAFF, validSchoolId, validStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_staff_with_valid_school_id_1_and_valid_staffId_10_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final long invalidStaffId = 10L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(StaffControllerUtilIT.DELETESTAFF, validSchoolId, invalidStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_staff_with_valid_school_id_1_and_invalid_staffId_negative_1_that_does_not_exist()
			throws Exception {
		final long validSchoolId = 1L;
		final long invalidStaffId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(StaffControllerUtilIT.DELETESTAFF, validSchoolId, invalidStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_staff_with_valid_school_id_1_and_invalid_staffId_one_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final String invalidStaffId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(StaffControllerUtilIT.DELETESTAFF, validSchoolId, invalidStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

}
