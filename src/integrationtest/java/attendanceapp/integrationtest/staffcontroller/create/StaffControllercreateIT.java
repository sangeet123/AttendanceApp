package attendanceapp.integrationtest.staffcontroller.create;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

public class StaffControllercreateIT extends TestConfigurerIT {

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
	public static void tearDown() throws Exception {
		AttendanceAppUtilIT.mysqlScriptRunner(StaffControllerUtilIT.CLEAR_STAFF_QUERY_SQL_SCRIPT_FILE_PATH);
	}

	@Test()
	public void create_valid_staff() throws Exception {
		final long schoolId = 1L;
		Map<String, Object> createObject = StaffControllerUtilIT.getStaffRequestObject("firstname", "lastname",
				"username", "password1A@", "staff@email.com", "2233562322", "st", "ROLE_TEACHER", "test staff");
		getMockMvc()
				.perform(post(StaffControllerUtilIT.CREATESTAFF, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(createObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.firstName", is("firstname"))).andExpect(jsonPath("$.lastName", is("lastname")))
				.andExpect(jsonPath("$.email", is("staff@email.com"))).andExpect(jsonPath("$.role", is("ROLE_TEACHER")))
				.andExpect(jsonPath("$.comment", is("test staff"))).andExpect(jsonPath("$.shortName", is("st")))
				.andExpect(jsonPath("$.telephone", is("2233562322")));
	}

	@Test()
	public void create_staff_with_email_that_exist_in_database() throws Exception {
		final long schoolId = 1L;
		final String duplicateEmail = "stafftest@email.com";
		Map<String, Object> createObject = StaffControllerUtilIT.getStaffRequestObject("firstname", "lastname",
				"username123", "password1A@", duplicateEmail, "2233562322", "stt", "ROLE_TEACHER", "test staff");
		getMockMvc()
				.perform(post(StaffControllerUtilIT.CREATESTAFF, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(createObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("staff.email")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Staff with email already exists. Please try with different email.")));
	}

	@Test()
	public void create_staff_with_short_name_that_exist_in_database() throws Exception {
		final long schoolId = 1L;
		final String duplicateShortName = "stu";
		Map<String, Object> createObject = StaffControllerUtilIT.getStaffRequestObject("firstname", "lastname",
				"username123", "password1A@", "stafftest11@email.com", "2233562322", duplicateShortName, "ROLE_TEACHER",
				"test staff");
		getMockMvc()
				.perform(post(StaffControllerUtilIT.CREATESTAFF, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(createObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("staff.shortname")))
				.andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
						"Staff with short name already exists. Please try with different short name.")));
		;
	}

	@Test()
	public void create_staff_with_user_name_that_exist_in_database() throws Exception {
		final long schoolId = 1L;
		final String duplicateUserName = "staffusername";
		Map<String, Object> createObject = StaffControllerUtilIT.getStaffRequestObject("firstname", "lastname",
				duplicateUserName, "password1A@", "stafftest10@email.com", "2233562322", "sn1", "ROLE_TEACHER",
				"test staff");
		getMockMvc()
				.perform(post(StaffControllerUtilIT.CREATESTAFF, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(createObject)))
				.andExpect(status().isConflict()).andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("staff.username")))
				.andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
						"Staff with username already exists. Please try with different user name.")));
	}

}
