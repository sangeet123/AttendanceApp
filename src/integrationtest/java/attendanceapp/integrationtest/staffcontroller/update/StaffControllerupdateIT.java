package attendanceapp.integrationtest.staffcontroller.update;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.StaffControllerUtilIT;

public class StaffControllerupdateIT extends TestConfigurerIT {

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
	public void update_valid_staff_that_exist_in_database() throws Exception {
		Map<String, Object> updateObject = StaffControllerUtilIT.getStaffUpdateRequestObject(1, "firstname", "lastname",
				"staffupdate@email.com", "4433562344", "sta", "ROLE_TEACHER", "test update staff");
		getMockMvc()
				.perform(put(StaffControllerUtilIT.UPDATESTAFF, 1)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(updateObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.firstName", is("firstname"))).andExpect(jsonPath("$.lastName", is("lastname")))
				.andExpect(jsonPath("$.email", is("staffupdate@email.com")))
				.andExpect(jsonPath("$.role", is("ROLE_TEACHER")))
				.andExpect(jsonPath("$.comment", is("test update staff"))).andExpect(jsonPath("$.shortName", is("sta")))
				.andExpect(jsonPath("$.telephone", is("4433562344")));
	}

	@Test()
	public void updating_staff_that_does_not_exist() throws Exception {
		int staffIdThatDoesNotExist = 2;
		Map<String, Object> updateObject = StaffControllerUtilIT.getStaffUpdateRequestObject(staffIdThatDoesNotExist,
				"firstname", "lastname", "staffupdate@email.com", "4433562344", "sta", "ROLE_TEACHER",
				"test update staff");
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(put(StaffControllerUtilIT.UPDATESTAFF, 1)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(updateObject)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));

	}

}
