package attendanceapp.integrationtest.subjectcontroller.deletesubjects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.SubjectControllerUtilIT;
import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;

public class SubjectControllerdeleteSubjectsIT extends TestConfigurerIT {

	private static boolean hasBeenSet = false;
	public static final String RESPONSE_JSON_STRING = "{\"statusCode\":1,\"messages\":[\"Subjects have been deleted successfully.\"]}";

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

	/*
	 * one is valid and the other one is not valid subject id. The left one id
	 * is a valid subject id.
	 */
	@Test()
	public void deleted_subjects_id_1_that_exist_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final long schoolId = 1L;
		final String commaSeparatedIds = "1,10";
		DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject = new DeleteSubjectsRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(
						delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId)
								.header(AttendanceAppUtilIT.AUTHORIZATION,
										SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
								.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
								.content(AttendanceAppUtilIT
										.convertObjectToJsonBytes(deleteSelectedSubjectRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(RESPONSE_JSON_STRING));
	}

	/*
	 * one is valid and the other one is not valid subject id. The right one id
	 * is a valid subject id.
	 */
	@Test()
	public void deleted_subjects_id_2_that_exist_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final String commaSeparatedIds = "10,2";
		final long schoolId = 1L;
		DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject = new DeleteSubjectsRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(
						delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId)
								.header(AttendanceAppUtilIT.AUTHORIZATION,
										SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
								.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
								.content(AttendanceAppUtilIT
										.convertObjectToJsonBytes(deleteSelectedSubjectRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(RESPONSE_JSON_STRING));
	}

	/*
	 * delete select operation is a bulk operation so if id is not found query
	 * simply ignore it.
	 */
	@Test()
	public void deleted_subjects_id_20_and_10_that_does_not_exist_for_school_id_1() throws Exception {
		final String commaSeparatedIds = "20,10";
		final long schoolId = 1L;
		DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject = new DeleteSubjectsRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(
						delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId)
								.header(AttendanceAppUtilIT.AUTHORIZATION,
										SubjectControllerUtilIT.BASIC_DIGEST_HEADER_VALUE)
								.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
								.content(AttendanceAppUtilIT
										.convertObjectToJsonBytes(deleteSelectedSubjectRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(RESPONSE_JSON_STRING));
	}
}
