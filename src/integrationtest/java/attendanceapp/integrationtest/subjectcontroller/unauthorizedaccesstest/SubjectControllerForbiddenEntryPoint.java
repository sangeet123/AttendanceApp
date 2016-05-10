package attendanceapp.integrationtest.subjectcontroller.unauthorizedaccesstest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;

public class SubjectControllerForbiddenEntryPoint extends TestConfigurerIT {
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

	@Test()
	public void get_subject_with_unauthorized_school_id_10_and_invalid_subjectId_one() throws Exception {
		final long invalidSchoolId = 10L;
		final String invalidSubjectId = "one";
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, invalidSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void get_subject_with_invalid_school_id_two_and_invalid_subjectId_one() throws Exception {
		final String invalidSchoolId = "two";
		final String invalidSubjectId = "one";
		getMockMvc()
				.perform(get(SubjectControllerUtilIT.GETSUBJECTWITHID, invalidSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void get_all_subjects_for_unauthorized_schoolId_10() throws Exception {
		final long invalidSchoolId = 10L;
		getMockMvc().perform(get(SubjectControllerUtilIT.GETALLSUBJECTS, invalidSchoolId).header("Authorization",
				basicDigestHeaderValue)).andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void get_all_subjects_for_invalid_schoolId_two() throws Exception {
		final String invalidSchoolId = "two";
		getMockMvc().perform(get(SubjectControllerUtilIT.GETALLSUBJECTS, invalidSchoolId).header("Authorization",
				basicDigestHeaderValue)).andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void deleted_subjects_id_2_and_10_for_unauthorized_school_id_10() throws Exception {
		final String commaSeparatedIds = "10,2";
		final long schoolId = 10L;
		DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject = new DeleteSubjectsRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc().perform(delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId).header("Authorization",
				basicDigestHeaderValue)).andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void deleted_subjects_id_2_and_10_for_invalid_school_id_two() throws Exception {
		final String commaSeparatedIds = "10,2";
		final String schoolId = "two";
		DeleteSubjectsRequestObject deleteSelectedSubjectRequestObject = new DeleteSubjectsRequestObject();
		deleteSelectedSubjectRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc().perform(delete(SubjectControllerUtilIT.DELETE_SUBJECTS, schoolId).header("Authorization",
				basicDigestHeaderValue)).andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void delete_subject_with_unauthorized_school_id_10_and_invalid_subjectId_one() throws Exception {
		final long invalidSchoolId = 10L;
		final String invalidSubjectId = "one";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, invalidSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isForbidden()).andExpect(content().string(""));
	}

	@Test()
	public void delete_subject_with_invalid_school_id_two_and_invalid_subjectId_one() throws Exception {
		final String invalidSchoolId = "two";
		final String invalidSubjectId = "one";
		getMockMvc()
				.perform(delete(SubjectControllerUtilIT.DELETESUBJECT, invalidSchoolId, invalidSubjectId)
						.header("Authorization", basicDigestHeaderValue))
				.andExpect(status().isForbidden()).andExpect(content().string(""));
	}

}
