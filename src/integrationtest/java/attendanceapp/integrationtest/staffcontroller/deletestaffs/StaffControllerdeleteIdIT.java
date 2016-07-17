package attendanceapp.integrationtest.staffcontroller.deletestaffs;

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
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;

public class StaffControllerdeleteIdIT extends TestConfigurerIT {
	private static boolean hasBeenSet = false;
	public static final String RESPONSE_JSON_STRING = "{\"statusCode\":1,\"messages\":[\"Staffs have been deleted successfully.\"]}";

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

	/*
	 * one is valid and the other one is not valid subject id. The left one id
	 * is a valid subject id.
	 */
	@Test()
	public void deleted_staffs_id_1_that_exist_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final long schoolId = 1L;
		final String commaSeparatedIds = "1,10";
		DeleteStaffsRequestObject deleteSelectedStaffRequestObject = new DeleteStaffsRequestObject();
		deleteSelectedStaffRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(delete(StaffControllerUtilIT.DELETE_STAFFS, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(deleteSelectedStaffRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(RESPONSE_JSON_STRING));
	}

	/*
	 * delete select operation is a bulk operation so if id is not found query
	 * simply ignore it.
	 */
	@Test()
	public void deleted_staffs_id_20_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final String commaSeparatedIds = "20,10";
		final long schoolId = 1L;
		DeleteStaffsRequestObject deleteSelectedStaffRequestObject = new DeleteStaffsRequestObject();
		deleteSelectedStaffRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(delete(StaffControllerUtilIT.DELETE_STAFFS, schoolId)
						.header(AttendanceAppUtilIT.AUTHORIZATION, StaffControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(deleteSelectedStaffRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(RESPONSE_JSON_STRING));
	}

}
