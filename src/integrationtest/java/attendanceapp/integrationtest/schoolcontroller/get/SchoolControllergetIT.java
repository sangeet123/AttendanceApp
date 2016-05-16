package attendanceapp.integrationtest.schoolcontroller.get;

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
import attendanceapp.integrationtest.utils.SchoolControllerUtilIT;

public class SchoolControllergetIT extends TestConfigurerIT {
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
	public void get_all_schools_that_exist_in_database() throws Exception {
		getMockMvc()
				.perform(get(SchoolControllerUtilIT.GETALLSCHOOLS).header(AttendanceAppUtilIT.AUTHORIZATION,
						SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("test1"))).andExpect(jsonPath("$[0].telephone", is("123456789")))
				.andExpect(jsonPath("$[0].email", is("test1@email.com"))).andExpect(jsonPath("$[1].id", is(10)))
				.andExpect(jsonPath("$[1].name", is("test10"))).andExpect(jsonPath("$[1].telephone", is("234567891")))
				.andExpect(jsonPath("$[1].email", is("test10@email.com")));
	}

}
