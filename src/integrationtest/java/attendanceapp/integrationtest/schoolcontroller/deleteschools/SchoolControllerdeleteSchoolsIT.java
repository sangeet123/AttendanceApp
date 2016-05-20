package attendanceapp.integrationtest.schoolcontroller.deleteschools;

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
import attendanceapp.model.requestobject.DeleteSchoolsRequestObject;

public class SchoolControllerdeleteSchoolsIT extends TestConfigurerIT {

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

	/*
	 * one is valid and the other one is not valid school id. The left one id is
	 * a valid school id.
	 */
	@Test()
	public void deleted_valid_selected_schoolid_1_that_exist_in_database() throws Exception {
		final String commaSeparatedIds = "1,15";
		DeleteSchoolsRequestObject deleteSelectedSchoolRequestObject = new DeleteSchoolsRequestObject();
		deleteSelectedSchoolRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Schools have been deleted successfully.\"]}";
		getMockMvc()
				.perform(
						delete(SchoolControllerUtilIT.DELETE_SELECTED_SCHOOL)
								.header(AttendanceAppUtilIT.AUTHORIZATION,
										SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
								.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
								.content(AttendanceAppUtilIT
										.convertObjectToJsonBytes(deleteSelectedSchoolRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	/*
	 * one is valid and the other one is not valid school id. The right one id
	 * is a valid school id.
	 */
	@Test()
	public void deleted_selected_schoolids_10_that_exist_in_database() throws Exception {
		final String commaSeparatedIds = "20,10";
		DeleteSchoolsRequestObject deleteSelectedSchoolRequestObject = new DeleteSchoolsRequestObject();
		deleteSelectedSchoolRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Schools have been deleted successfully.\"]}";
		getMockMvc()
				.perform(
						delete(SchoolControllerUtilIT.DELETE_SELECTED_SCHOOL)
								.header(AttendanceAppUtilIT.AUTHORIZATION,
										SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
								.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
								.content(AttendanceAppUtilIT
										.convertObjectToJsonBytes(deleteSelectedSchoolRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	/*
	 * delete select operation is a bulk operation so if id is not found query
	 * simply ignore it.
	 */
	@Test()
	public void deleted_selected_schoolids_that_does_not_exist_in_database() throws Exception {
		final String commaSeparatedIds = "20,10";
		DeleteSchoolsRequestObject deleteSelectedSchoolRequestObject = new DeleteSchoolsRequestObject();
		deleteSelectedSchoolRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Schools have been deleted successfully.\"]}";
		getMockMvc()
				.perform(
						delete(SchoolControllerUtilIT.DELETE_SELECTED_SCHOOL)
								.header(AttendanceAppUtilIT.AUTHORIZATION,
										SchoolControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
								.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
								.content(AttendanceAppUtilIT
										.convertObjectToJsonBytes(deleteSelectedSchoolRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}
}
