package attendanceapp.integrationtest.staffcontroller.get;

import static org.hamcrest.Matchers.hasSize;
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

public class StaffControllergetIT extends TestConfigurerIT {
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
	public void get_all_staffs_that_exist_in_database() throws Exception {
		getMockMvc()
				.perform(get(StaffControllerUtilIT.GETALLSTAFFS, 1).header(AttendanceAppUtilIT.AUTHORIZATION,
						StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].createdOn", is("2016-05-01T14:39:56")))
				.andExpect(jsonPath("$[0].updatedOn", is("2016-05-01T14:39:56")))
				.andExpect(jsonPath("$[0].firstName", is("stafffirstname")))
				.andExpect(jsonPath("$[0].lastName", is("stafflastname")))
				.andExpect(jsonPath("$[0].role", is("ROLE_TEACHER")))
				.andExpect(jsonPath("$[0].telephone", is("1234569411")))
				.andExpect(jsonPath("$[0].shortName", is("stu")))
				.andExpect(jsonPath("$[0].comment", is("staff test user")))
				.andExpect(jsonPath("$[0].email", is("stafftest@email.com"))).andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].createdOn", is("2016-05-01T14:39:56")))
				.andExpect(jsonPath("$[1].updatedOn", is("2016-05-01T14:39:56")))
				.andExpect(jsonPath("$[1].firstName", is("stafffirstname1")))
				.andExpect(jsonPath("$[1].lastName", is("stafflastname1")))
				.andExpect(jsonPath("$[1].role", is("ROLE_TEACHER")))
				.andExpect(jsonPath("$[1].telephone", is("1234569411")))
				.andExpect(jsonPath("$[1].shortName", is("stu1")))
				.andExpect(jsonPath("$[1].comment", is("staff test user")))
				.andExpect(jsonPath("$[1].email", is("stafftest1@email.com")));
	}

}
