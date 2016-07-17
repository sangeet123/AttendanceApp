package attendanceapp.unittest.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import attendanceapp.constants.Constant;
import attendanceapp.controller.SubjectController;
import attendanceapp.controllerimpl.SubjectControllerImpl;
import attendanceapp.dao.validator.SubjectDaoValidator;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.model.requestobject.DeleteSubjectsRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.services.SubjectService;
import attendanceapp.unittest.common.util.AttendanceAppUnitTestUtil;
import attendanceapp.unittest.common.util.UnitTestConfigurer;
import attendanceapp.unittest.testdata.SubjectControllerUnitTestData;
import attendanceapp.unittest.utils.SubjectControllerUnitTestUtil;

public class SubjectControllerUnitTest extends UnitTestConfigurer {

	private final long SCHOOLID = 1L;

	@Autowired()
	private SubjectService subjectServiceMock;

	@Configuration()
	@EnableWebMvc()
	public static class SchoolTestConfiguration {
		@Bean
		public SubjectController subjectController() {
			return new SubjectControllerImpl();
		}
	}

	@Test()
	public void get_subject_should_return_all_registered_schools() throws Exception {

		List<SubjectResponseObject> testSubjects = SubjectControllerUnitTestData.getTestSubjectList();

		when(subjectServiceMock.getSubjectList(SCHOOLID)).thenReturn(testSubjects);

		ResultActions resultAction = getMockMvc().perform(get(SubjectControllerUnitTestUtil.GETALLSUBJECTS, SCHOOLID))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(testSubjects.size())));

		for (int i = 0; i < testSubjects.size(); ++i) {
			String jasonPath = String.format(AttendanceAppUnitTestUtil.JASONLIST, i);
			SubjectResponseObject schoolResponseObject = testSubjects.get(i);
			resultAction.andExpect(jsonPath(jasonPath.concat(SubjectControllerUnitTestUtil.ID),
					is((int) schoolResponseObject.getId())));
			resultAction.andExpect(
					jsonPath(jasonPath.concat(SubjectControllerUnitTestUtil.NAME), is(schoolResponseObject.getName())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SubjectControllerUnitTestUtil.SHORTNAME),
					is(schoolResponseObject.getShortName())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SubjectControllerUnitTestUtil.CREDIT),
					is(schoolResponseObject.getCredit())));
		}
		verify(subjectServiceMock, atLeast(1)).getSubjectList(SCHOOLID);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test()
	public void get_subject_by_id_should_return_subject_for_valid_school_id_and_subject_id_that_exist()
			throws Exception {

		long subjectId = 2;
		List<SubjectResponseObject> subjectsResponseObject = SubjectControllerUnitTestData.getSubject(subjectId);
		SubjectResponseObject returnedSchoolResponseObject = subjectsResponseObject.get(0);
		when(subjectServiceMock.getSubject(SCHOOLID, subjectId)).thenReturn(returnedSchoolResponseObject);

		getMockMvc().perform(get(SubjectControllerUnitTestUtil.GETSUBJECTWITHID, SCHOOLID, subjectId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SubjectControllerUnitTestUtil.ID),
						is((int) returnedSchoolResponseObject.getId())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SubjectControllerUnitTestUtil.NAME),
						is(returnedSchoolResponseObject.getName())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SubjectControllerUnitTestUtil.SHORTNAME),
						is(returnedSchoolResponseObject.getShortName())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SubjectControllerUnitTestUtil.CREDIT),
						is(returnedSchoolResponseObject.getCredit())));
		verify(subjectServiceMock, atLeast(1)).getSubject(SCHOOLID, subjectId);
		verifyNoMoreInteractions(subjectServiceMock);

	}

	@Test()
	public void get_subject_by_id_should_return_http_status_code_404_for_valid_subject_id_that_does_not_exist()
			throws Exception {
		long validIdThatDoesNotExist = 11L;

		when(subjectServiceMock.getSubject(SCHOOLID, validIdThatDoesNotExist))
				.thenThrow(new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST));
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc().perform(get(SubjectControllerUnitTestUtil.GETSUBJECTWITHID, SCHOOLID, validIdThatDoesNotExist))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(subjectServiceMock, atLeast(1)).getSubject(SCHOOLID, validIdThatDoesNotExist);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test()
	public void get_subject_by_id_should_return_http_status_code_404_for_id_that_is_invalid() throws Exception {
		long validIdThatDoesNotExist = -1L;

		when(subjectServiceMock.getSubject(SCHOOLID, validIdThatDoesNotExist))
				.thenThrow(new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST));
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc().perform(get(SubjectControllerUnitTestUtil.GETSUBJECTWITHID, SCHOOLID, validIdThatDoesNotExist))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(subjectServiceMock, atLeast(1)).getSubject(SCHOOLID, validIdThatDoesNotExist);
		verifyNoMoreInteractions(subjectServiceMock);

	}

	@Test()
	public void delete_subject_by_id_should_return_http_status_code_404_for_id_that_is_valid_but_does_not_exist()
			throws Exception {
		long validSubjectIdThatDoesNotExist = 15L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		doThrow(new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST)).when(subjectServiceMock).delete(SCHOOLID,
				validSubjectIdThatDoesNotExist);
		getMockMvc()
				.perform(delete(SubjectControllerUnitTestUtil.DELETESUBJECT, SCHOOLID, validSubjectIdThatDoesNotExist))
				.andExpect(status().isNotFound()).andExpect(content().string(responseJsonString));
		verify(subjectServiceMock, atLeast(1)).delete(SCHOOLID, validSubjectIdThatDoesNotExist);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test()
	public void delete_subject_by_id_should_return_http_status_code_404_for_id_that_is_invalid() throws Exception {
		long invalidSubjectIdThatDoesNotExist = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		doThrow(new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST)).when(subjectServiceMock).delete(SCHOOLID,
				invalidSubjectIdThatDoesNotExist);
		getMockMvc()
				.perform(
						delete(SubjectControllerUnitTestUtil.DELETESUBJECT, SCHOOLID, invalidSubjectIdThatDoesNotExist))
				.andExpect(status().isNotFound()).andExpect(content().string(responseJsonString));
		verify(subjectServiceMock, atLeast(1)).delete(SCHOOLID, invalidSubjectIdThatDoesNotExist);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test()
	public void create_subject_should_return_http_status_code_of_200() throws Exception {
		SubjectCreateRequestObject subjectCreateRequestObject = SubjectControllerUnitTestUtil
				.getSubjectCreateRequestObject(1, "Java Programming", "JP 501", 5);
		SubjectResponseObject subjectResponseObject = SubjectControllerUnitTestUtil.getSubjectResponseObject(1,
				"Java Programming", "JP 501", 5);
		when(subjectServiceMock.create(SCHOOLID, subjectCreateRequestObject)).thenReturn(subjectResponseObject);
		getMockMvc()
				.perform(post(SubjectControllerUnitTestUtil.CREATESUBJECT, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(subjectCreateRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)).andExpect(
						content().string(AttendanceAppUnitTestUtil.convertObjectToJsonString(subjectResponseObject)));
		verify(subjectServiceMock, atLeast(1)).create(SCHOOLID, subjectCreateRequestObject);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test()
	public void create_subject_should_return_validation_error_for_empty_subject_in_english_language() throws Exception {
		SubjectCreateRequestObject subjectCreateRequestObject = new SubjectCreateRequestObject();
		getMockMvc()
				.perform(post(SubjectControllerUnitTestUtil.CREATESUBJECT, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(subjectCreateRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(2)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "shortName")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Subject name cannot be empty.", "Subject short name cannot be empty.")));

		verifyZeroInteractions(subjectServiceMock);

	}

	@Test()
	public void update_school_should_return_http_status_code_200_and_school_response_object() throws Exception {
		SubjectUpdateRequestObject subjectUpdateRequestObject = SubjectControllerUnitTestUtil
				.getSubjectUpdateRequestObject(1, "Java Programming", "JP 501", 5);
		SubjectResponseObject subjectResponseObject = SubjectControllerUnitTestUtil.getSubjectResponseObject(1,
				"Java Programming", "JP 501", 5);
		when(subjectServiceMock.update(SCHOOLID, subjectUpdateRequestObject)).thenReturn(subjectResponseObject);
		getMockMvc()
				.perform(put(SubjectControllerUnitTestUtil.UPDATESUBJECT, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(subjectUpdateRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)).andExpect(
						content().string(AttendanceAppUnitTestUtil.convertObjectToJsonString(subjectResponseObject)));
		verify(subjectServiceMock, atLeast(1)).update(SCHOOLID, subjectUpdateRequestObject);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test()
	public void update_school_should_return_validation_error_for_empty_school_in_english_language() throws Exception {
		SubjectUpdateRequestObject subjectUpdateRequestObject = new SubjectUpdateRequestObject();
		getMockMvc()
				.perform(put(SubjectControllerUnitTestUtil.UPDATESUBJECT, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(subjectUpdateRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(2)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("name", "shortName")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Subject name cannot be empty.", "Subject short name cannot be empty.")));

		verifyZeroInteractions(subjectServiceMock);
	}

	@Test
	public void update_school_should_return_duplicate_school_name_exception_for_school_that_already_exist()
			throws Exception {
		final String duplicateSubjectShortName = "CP 101";
		SubjectUpdateRequestObject subjectUpdateRequestObject = SubjectControllerUnitTestUtil
				.getSubjectUpdateRequestObject(1, "Java Programming", duplicateSubjectShortName, 5);
		Set<String> fieldErrors = new HashSet<>();
		fieldErrors.add(SubjectDaoValidator.INVALID_SHORTNAME);
		doThrow(new ConflictException(fieldErrors)).when(subjectServiceMock).update(SCHOOLID,
				subjectUpdateRequestObject);
		getMockMvc()
				.perform(put(SubjectControllerUnitTestUtil.UPDATESUBJECT, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(subjectUpdateRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("subject.shortname")))
				.andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder(
						"Subject with given short name already exists. Please enter different short name.")));
		verify(subjectServiceMock, atLeast(1)).update(SCHOOLID, subjectUpdateRequestObject);
		verifyNoMoreInteractions(subjectServiceMock);

	}

	@Test
	public void update_school_should_return_school_not_found_exception_for_school_that_does_not_exist()
			throws Exception {
		final long nonExistantSubjectId = 50;
		SubjectUpdateRequestObject subjectUpdateRequestObject = SubjectControllerUnitTestUtil
				.getSubjectUpdateRequestObject(nonExistantSubjectId, "Java Programming", "JP 501", 5);
		doThrow(new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST)).when(subjectServiceMock).update(SCHOOLID,
				subjectUpdateRequestObject);
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(put(SubjectControllerUnitTestUtil.UPDATESUBJECT, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(subjectUpdateRequestObject)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(subjectServiceMock, atLeast(1)).update(SCHOOLID, subjectUpdateRequestObject);
		verifyNoMoreInteractions(subjectServiceMock);
	}

	@Test
	public void delete_selected_school_should_return_validation_errors_for_invalid_format_input() throws Exception {
		final String commaSeparatedIds = "20,10,";
		DeleteSubjectsRequestObject deleteSubjectsRequestObject = new DeleteSubjectsRequestObject();
		deleteSubjectsRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(delete(SubjectControllerUnitTestUtil.DELETE_SUBJECTS, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(deleteSubjectsRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("commaSeparatedIds")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Invalid input format. Ids should be seperated by comma.")));
		verifyZeroInteractions(subjectServiceMock);
	}

	@Test
	public void delete_selected_school_should_return_validation_errors_for_invalid_empty_input() throws Exception {
		DeleteSubjectsRequestObject deleteSubjectsRequestObject = new DeleteSubjectsRequestObject();
		getMockMvc()
				.perform(delete(SubjectControllerUnitTestUtil.DELETE_SUBJECTS, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(deleteSubjectsRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("commaSeparatedIds")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Please provide at least one id to delete.")));
		verifyZeroInteractions(subjectServiceMock);
	}

	@Test
	public void delete_selected_school_should_return_success_message_for_valid_input() throws Exception {
		final String commaSeparatedIds = "20,10";
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Subjects have been deleted successfully.\"]}";
		DeleteSubjectsRequestObject deleteSubjectsRequestObject = new DeleteSubjectsRequestObject();
		deleteSubjectsRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(delete(SubjectControllerUnitTestUtil.DELETE_SUBJECTS, SCHOOLID)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(deleteSubjectsRequestObject)))
				.andExpect(content().string(responseJsonString));
		verify(subjectServiceMock, atLeast(1)).delete(SCHOOLID, deleteSubjectsRequestObject);
		verifyNoMoreInteractions(subjectServiceMock);
	}

}
