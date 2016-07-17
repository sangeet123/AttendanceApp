package attendanceapp.integrationtest.subjectcontroller.update;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.unittest.common.util.AttendanceAppUnitTestUtil;

public class SubjectControllerupdateIT extends TestConfigurerIT {

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
	public void update_valid_subject_that_exist_in_database() throws Exception {
		/*
		 * There is no way to verify created and updated date therefore jsonPath
		 * is used for the field that are known. Could be done after doing get
		 * for the updated resources.
		 */
		final long schoolId = 1L;
		SubjectUpdateRequestObject updateObject = SubjectControllerUtilIT
				.getSubjectUpdateRequestObject("Computer Programming", "CP401", 5, 1);
		getMockMvc()
				.perform(put(SubjectControllerUtilIT.UPDATESUBJECT, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(updateObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name", is("Computer Programming")))
				.andExpect(jsonPath("$.shortName", is("CP401"))).andExpect(jsonPath("$.credit", is(5)));
	}

	@Test()
	public void updating_to_subject_short_name_that_already_exist() throws Exception {
		final long schoolId = 1L;
		SubjectUpdateRequestObject updateObject = SubjectControllerUtilIT
				.getSubjectUpdateRequestObject("Computer Programming", "JP301", 5, 1);
		getMockMvc()
				.perform(put(SubjectControllerUtilIT.UPDATESUBJECT, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(updateObject)))
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("subject.shortname")))
				.andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
						"Subject with given short name already exists. Please enter different short name.")));
	}

	@Test()
	public void updating_subject_that_does_not_exist() throws Exception {
		final long schoolId = 1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		SubjectUpdateRequestObject updateObject = SubjectControllerUtilIT
				.getSubjectUpdateRequestObject("Computer Programming", "JP301", 5, 10);
		getMockMvc()
				.perform(put(SubjectControllerUtilIT.UPDATESUBJECT, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(updateObject)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));

	}

}
