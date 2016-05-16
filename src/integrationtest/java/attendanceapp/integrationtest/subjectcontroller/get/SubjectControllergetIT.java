package attendanceapp.integrationtest.subjectcontroller.get;

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
import attendanceapp.integrationtest.utils.SubjectControllerUtilIT;

public class SubjectControllergetIT extends TestConfigurerIT {
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
	public void get_all_schools_that_exist_in_database() throws Exception {
		final long schoolIdForCourses = 1L;
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETALLSUBJECTS, schoolIdForCourses)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Computer Programming")))
				.andExpect(jsonPath("$[0].shortName", is("CP101"))).andExpect(jsonPath("$[0].credit", is(5)))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].name", is("Java Programming")))
				.andExpect(jsonPath("$[1].shortName", is("JP301"))).andExpect(jsonPath("$[0].credit", is(5)));
	}

}
