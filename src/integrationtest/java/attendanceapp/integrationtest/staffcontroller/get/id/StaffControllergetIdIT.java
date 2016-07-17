package attendanceapp.integrationtest.staffcontroller.get.id;

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
import attendanceapp.integrationtest.utils.StaffControllerUtilIT;

public class StaffControllergetIdIT extends TestConfigurerIT {
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
	public void get_staff_with_valid_id_1_that_exist() throws Exception {
		getMockMvc()
				.perform(get(StaffControllerUtilIT.GETSTAFFWITHID, 1, 1).header(AttendanceAppUtilIT.AUTHORIZATION,
						StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.createdOn", is("2016-05-01T14:39:56")))
				.andExpect(jsonPath("$.updatedOn", is("2016-05-01T14:39:56")))
				.andExpect(jsonPath("$.firstName", is("stafffirstname")))
				.andExpect(jsonPath("$.lastName", is("stafflastname")))
				.andExpect(jsonPath("$.role", is("ROLE_TEACHER"))).andExpect(jsonPath("$.telephone", is("1234569411")))
				.andExpect(jsonPath("$.shortName", is("stu"))).andExpect(jsonPath("$.comment", is("staff test user")))
				.andExpect(jsonPath("$.email", is("stafftest@email.com")));
	}

	@Test()
	public void get_staff_with_valid_school_id_1_and_invalid_staffId_10_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final long invalidStaffId = 10L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(get(StaffControllerUtilIT.GETSTAFFWITHID, validSchoolId, invalidStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_subject_with_valid_school_id_1_and_invalid_staffId_negative_1_that_does_not_exist()
			throws Exception {
		final long validSchoolId = 1L;
		final long invalidStaffId = 10L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(get(StaffControllerUtilIT.GETSTAFFWITHID, validSchoolId, invalidStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_subject_with_valid_school_id_1_and_invalid_staffId_one_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final String invalidStaffId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(get(StaffControllerUtilIT.GETSTAFFWITHID, validSchoolId, invalidStaffId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

}
