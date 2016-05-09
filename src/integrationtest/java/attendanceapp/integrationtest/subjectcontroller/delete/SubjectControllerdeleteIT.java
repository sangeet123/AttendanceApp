package attendanceapp.integrationtest.subjectcontroller.delete;

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

public class SubjectControllerdeleteIT extends TestConfigurerIT {

	public static final String basicDigestHeaderValue = "Basic "
			+ new String(Base64.encode(("subjecttestuser:password").getBytes()));
	public static final String insertSchoolQuerySQLScriptFilePath = "it-insert-subject-queries.sql";
	public static final String clearSchoolQuerySQLScriptFilePath = "it-delete-subject-queries.sql";
	public static boolean isSettedUp = false;

	@Before()
	public void setUp() {
		super.setUp();
		if (!isSettedUp) {
			try {
				AttendanceAppUtilIT.mysqlScriptRunner(insertSchoolQuerySQLScriptFilePath);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			isSettedUp = true;
		}
	}

	@AfterClass()
	public static void tearDown() {
		try {
			AttendanceAppUtilIT.mysqlScriptRunner(clearSchoolQuerySQLScriptFilePath);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Test()
	public void delete_school_with_valid_id_1_and_valid_subject_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final long validSubjectId = 1L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Subject has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, validSchoolId, validSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_valid_id_1_and_valid_subject_id_2_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final long validSubjectId = 2L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Subject has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, validSchoolId, validSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_subject_with_valid_school_id_1_and_invalid_subjectId_10_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final long invalidSubjectId = 10L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Subject does not exist.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, validSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_subject_with_valid_school_id_1_and_invalid_subjectId_negative_1_that_does_not_exist()
			throws Exception {
		final long validSchoolId = 1L;
		final long invalidSubjectId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Subject does not exist.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, validSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_subject_with_valid_school_id_1_and_invalid_subjectId_one_that_does_not_exist() throws Exception {
		final long validSchoolId = 1L;
		final String invalidSubjectId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, validSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_subject_with_invalid_school_id_10_and_invalid_subjectId_one_that_does_not_exist()
			throws Exception {
		final long invalidSchoolId = 10L;
		final String invalidSubjectId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, invalidSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_subject_with_invalid_school_id_two_and_invalid_subjectId_one_that_does_not_exist()
			throws Exception {
		final String invalidSchoolId = "two";
		final String invalidSubjectId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, invalidSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}
}
