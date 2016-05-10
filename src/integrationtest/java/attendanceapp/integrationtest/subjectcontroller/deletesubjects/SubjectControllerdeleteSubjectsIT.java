package attendanceapp.integrationtest.subjectcontroller.deletesubjects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.codec.Base64;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.SubjectControllerUtilIT;
import attendanceapp.model.requestobject.DeleteSelectedSubjectRequestObject;

public class SubjectControllerdeleteSubjectsIT extends TestConfigurerIT {

	public static final String basicDigestHeaderValue = "Basic "
			+ new String(Base64.encode(("subjecttestuser:password").getBytes()));
	public static final String insertSubjectQuerySQLScriptFilePath = "it-insert-subject-queries.sql";
	public static final String clearSubjectQuerySQLScriptFilePath = "it-delete-subject-queries.sql";
	public static boolean isSettedUp = false;

	@Before()
	public void setUp() {
		super.setUp();
		if (!isSettedUp) {
			try {
				AttendanceAppUtilIT.mysqlScriptRunner(insertSubjectQuerySQLScriptFilePath);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			isSettedUp = true;
		}
	}

	@AfterClass()
	public static void tearDown() {
		try {
			AttendanceAppUtilIT.mysqlScriptRunner(clearSubjectQuerySQLScriptFilePath);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * one is valid and the other one is not valid subject id. The left one id
	 * is a valid subject id.
	 */
	@Test()
	public void deleted_subjects_id_1_that_exist_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final long schoolId = 1L;
		final String commaSeparatedIds = "1,10";
		DeleteSelectedSubjectRequestObject deleteSelectedSubjectRequestObject = new DeleteSelectedSubjectRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Subjects have been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId)
						.header("Authorization", basicDigestHeaderValue)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(deleteSelectedSubjectRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	/*
	 * one is valid and the other one is not valid subject id. The right one id
	 * is a valid subject id.
	 */
	@Test()
	public void deleted_subjects_id_2_that_exist_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final String commaSeparatedIds = "10,2";
		final long schoolId = 1L;
		DeleteSelectedSubjectRequestObject deleteSelectedSubjectRequestObject = new DeleteSelectedSubjectRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Subjects have been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId)
						.header("Authorization", basicDigestHeaderValue)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(deleteSelectedSubjectRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	/*
	 * delete select operation is a bulk operation so if id is not found query
	 * simply ignore it.
	 */
	@Test()
	public void deleted_subjects_id_20_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final String commaSeparatedIds = "20,10";
		final long schoolId = 1L;
		DeleteSelectedSubjectRequestObject deleteSelectedSubjectRequestObject = new DeleteSelectedSubjectRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Subjects have been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId)
						.header("Authorization", basicDigestHeaderValue)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(deleteSelectedSubjectRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}
}
