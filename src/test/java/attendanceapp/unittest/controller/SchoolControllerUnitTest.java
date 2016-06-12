package attendanceapp.unittest.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
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

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import attendanceapp.constants.SchoolRestControllerConstants;
import attendanceapp.controller.SchoolController;
import attendanceapp.controllerimpl.SchoolControllerImpl;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.model.requestobject.DeleteSchoolsRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.services.SchoolService;
import attendanceapp.unitest.common.util.AttendanceAppUnitTestUtil;
import attendanceapp.unitest.common.util.UnitTestConfigurer;
import attendanceapp.unittest.testdata.SchoolControllerUnitTestData;
import attendanceapp.unittest.utils.SchoolControllerUnitTestUtil;

public class SchoolControllerUnitTest extends UnitTestConfigurer {

	@Autowired()
	private SchoolService schoolServiceMock;

	@Configuration()
	@EnableWebMvc()
	public static class SchoolTestConfiguration {
		@Bean
		public SchoolController schoolController() {
			return new SchoolControllerImpl();
		}
	}

	@Test()
	public void get_school_should_return_all_registered_schools() throws Exception {

		List<SchoolResponseObject> testSchools = SchoolControllerUnitTestData.getTestSchoolList();

		when(schoolServiceMock.getSchoolList()).thenReturn(testSchools);

		ResultActions resultAction = getMockMvc().perform(get(SchoolControllerUnitTestUtil.GETALLSCHOOLS))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(testSchools.size())));

		for (int i = 0; i < testSchools.size(); ++i) {
			String jasonPath = String.format(AttendanceAppUnitTestUtil.JASONLIST, i);
			SchoolResponseObject schoolResponseObject = testSchools.get(i);
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestUtil.ID),
					is((int) schoolResponseObject.getId())));
			resultAction.andExpect(
					jsonPath(jasonPath.concat(SchoolControllerUnitTestUtil.NAME), is(schoolResponseObject.getName())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestUtil.EMAIL),
					is(schoolResponseObject.getEmail())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestUtil.TELEPHONE),
					is(schoolResponseObject.getTelephone())));
		}
		verify(schoolServiceMock, atLeast(1)).getSchoolList();
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void get_school_by_id_should_return_school_for_valid_id_and_school_that_exist() throws Exception {

		long id = 5;
		List<SchoolResponseObject> schoolsResponseObject = SchoolControllerUnitTestData.getSchool(id);
		SchoolResponseObject returnedSchoolResponseObject = schoolsResponseObject.get(0);
		when(schoolServiceMock.getSchool(id)).thenReturn(returnedSchoolResponseObject);

		getMockMvc().perform(get(SchoolControllerUnitTestUtil.GETSCHOOLWITHID, id)).andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestUtil.ID),
						is((int) returnedSchoolResponseObject.getId())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestUtil.NAME),
						is(returnedSchoolResponseObject.getName())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestUtil.EMAIL),
						is(returnedSchoolResponseObject.getEmail())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestUtil.TELEPHONE),
						is(returnedSchoolResponseObject.getTelephone())));
		verify(schoolServiceMock, atLeast(1)).getSchool(id);
		verifyNoMoreInteractions(schoolServiceMock);

	}

	@Test()
	public void get_school_by_id_should_return_http_status_code_404_for_valid_id_but_school_that_does_not_exist()
			throws Exception {
		long validIdThatDoesNotExist = 11L;
		when(schoolServiceMock.getSchool(validIdThatDoesNotExist))
				.thenThrow(new NotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST));
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"School does not exist.\"]}";
		getMockMvc().perform(get(SchoolControllerUnitTestUtil.GETSCHOOLWITHID, validIdThatDoesNotExist))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).getSchool(validIdThatDoesNotExist);
		verifyNoMoreInteractions(schoolServiceMock);

	}

	@Test()
	public void get_school_by_id_should_return_http_status_code_404_for_id_that_is_invalid() throws Exception {
		long invalidSchoolId = -1L;
		when(schoolServiceMock.getSchool(invalidSchoolId))
				.thenThrow(new NotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST));
		getMockMvc().perform(get(SchoolControllerUnitTestUtil.GETSCHOOLWITHID, invalidSchoolId))
				.andExpect(status().isNotFound());
		verify(schoolServiceMock, atLeast(1)).getSchool(invalidSchoolId);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void delete_school_by_id_should_return_http_status_code_404_for_id_that_is_valid_but_does_not_exist()
			throws Exception {
		long validSchoolIdThatDoesNotExist = 15L;
		doThrow(new NotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST)).when(schoolServiceMock)
				.delete(validSchoolIdThatDoesNotExist);
		getMockMvc().perform(delete(SchoolControllerUnitTestUtil.DELETESCHOOL, validSchoolIdThatDoesNotExist))
				.andExpect(status().isNotFound());
		verify(schoolServiceMock, atLeast(1)).delete(validSchoolIdThatDoesNotExist);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void delete_school_by_id_should_return_http_status_code_404_for_id_that_is_invalid() throws Exception {
		long invalidSchoolId = -1L;
		doThrow(new NotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST)).when(schoolServiceMock)
				.delete(invalidSchoolId);
		getMockMvc().perform(delete(SchoolControllerUnitTestUtil.DELETESCHOOL, invalidSchoolId))
				.andExpect(status().isNotFound());
		verify(schoolServiceMock, atLeast(1)).delete(invalidSchoolId);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void create_school_should_return_http_status_code_of_200() throws Exception {
		SchoolCreateRequestObject schoolRequestObject = SchoolControllerUnitTestUtil.getSchoolCreateRequestObject(
				"testschool15", "Rujesh1@", "Test School", "testemail@email.com", "2453469123");
		doNothing().when(schoolServiceMock).create(schoolRequestObject);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"School created sucessfully.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerUnitTestUtil.CREATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).create(schoolRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void create_school_should_return_validation_error_for_empty_school_in_english_language() throws Exception {
		SchoolCreateRequestObject schoolRequestObject = new SchoolCreateRequestObject();
		getMockMvc()
				.perform(post(SchoolControllerUnitTestUtil.CREATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(5)))
				.andExpect(jsonPath("$.fieldErrors[*].field",
						containsInAnyOrder("telephone", "name", "email", "username", "password")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Telephone number cannot be empty.", "School name cannot be empty.",
								"School email cannot be empty.", "username cannot be empty.",
								"password cannot be empty.")));

		verifyZeroInteractions(schoolServiceMock);

	}

	@Test()
	public void create_school_should_return_validation_error_for_invalid_telephone_invalid_email_and_null_school_name_in_english_language()
			throws Exception {
		SchoolCreateRequestObject schoolRequestObject = SchoolControllerUnitTestUtil.getSchoolCreateRequestObject(null,
				null, null, "abc@", "1234");
		getMockMvc()
				.perform(post(SchoolControllerUnitTestUtil.CREATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(5)))
				.andExpect(jsonPath("$.fieldErrors[*].field",
						containsInAnyOrder("telephone", "name", "email", "username", "password")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("School email is not a valid email. Please enter the valid email address.",
								"Invalid telephone number. Please enter the valid telephone no.",
								"School name cannot be empty.", "username cannot be empty.",
								"password cannot be empty.")));

		verifyZeroInteractions(schoolServiceMock);
	}

	@Test()
	public void update_school_should_return_http_status_code_200_and_school_response_object() throws Exception {
		SchoolUpdateRequestObject schoolUpdateRequestObject = SchoolControllerUnitTestUtil
				.getSchoolUpdateRequestObject(1L, "Test School Update", "testemail@email.com", "2453469123");
		SchoolResponseObject schoolResponseObject = SchoolControllerUnitTestUtil.getSchoolResponseObject(1L,
				"Test School Update", "testemail@email.com", "2453469123");
		String responseJsonString = AttendanceAppUnitTestUtil.convertObjectToJsonString(schoolResponseObject);
		when(schoolServiceMock.update(schoolUpdateRequestObject)).thenReturn(schoolResponseObject);
		getMockMvc()
				.perform(put(SchoolControllerUnitTestUtil.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).update(schoolUpdateRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void update_school_should_return_validation_error_for_empty_school_in_english_language() throws Exception {
		SchoolUpdateRequestObject schoolUpdateRequestObject = new SchoolUpdateRequestObject();
		getMockMvc()
				.perform(put(SchoolControllerUnitTestUtil.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(3)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("telephone", "name", "email")))
				.andExpect(jsonPath("$.fieldErrors[*].message", containsInAnyOrder("Telephone number cannot be empty.",
						"School name cannot be empty.", "School email cannot be empty.")));
		verifyZeroInteractions(schoolServiceMock);

	}

	@Test()
	public void update_school_should_return_validation_error_for_invalid_telephone_invalid_email_and_null_school_name_in_english_language()
			throws Exception {
		SchoolUpdateRequestObject schoolUpdateRequestObject = new SchoolUpdateRequestObject();
		schoolUpdateRequestObject.setEmail("abc@");
		schoolUpdateRequestObject.setTelephone("1234");

		getMockMvc()
				.perform(put(SchoolControllerUnitTestUtil.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(3)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("telephone", "name", "email")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("School email is not a valid email. Please enter the valid email address.",
								"Invalid telephone number. Please enter the valid telephone no.",
								"School name cannot be empty.")));
		verifyZeroInteractions(schoolServiceMock);
	}

	@Test
	public void update_school_should_return_duplicate_school_name_exception_for_school_that_already_exist()
			throws Exception {
		final String duplicateSchoolName = "Test School";
		SchoolUpdateRequestObject schoolUpdateRequestObject = SchoolControllerUnitTestUtil
				.getSchoolUpdateRequestObject(1L, duplicateSchoolName, "testemail@email.com", "2453469123");
		doThrow(new ConflictException(SchoolRestControllerConstants.DUPLICATE_SCHOOL_NAME)).when(schoolServiceMock)
				.update(schoolUpdateRequestObject);
		final String responseJsonString = "{\"statusCode\":3,\"messages\":[\"School with given name already exists. Please enter different name.\"]}";
		getMockMvc()
				.perform(put(SchoolControllerUnitTestUtil.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).update(schoolUpdateRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test
	public void update_school_should_return_school_not_found_exception_for_school_that_does_not_exist()
			throws Exception {
		final long nonExistantSchoolId = 10L;
		SchoolUpdateRequestObject schoolUpdateRequestObject = SchoolControllerUnitTestUtil
				.getSchoolUpdateRequestObject(nonExistantSchoolId, "Test School", "testemail@email.com", "2453469123");
		doThrow(new NotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST)).when(schoolServiceMock)
				.update(schoolUpdateRequestObject);
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"School does not exist.\"]}";
		getMockMvc()
				.perform(put(SchoolControllerUnitTestUtil.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).update(schoolUpdateRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test
	public void delete_selected_school_should_return_validation_errors_for_invalid_format_input() throws Exception {
		final String commaSeparatedIds = "20,10,";
		DeleteSchoolsRequestObject deleteSchoolsRequestObject = new DeleteSchoolsRequestObject();
		deleteSchoolsRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		getMockMvc()
				.perform(delete(SchoolControllerUnitTestUtil.DELETE_SCHOOLS)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(deleteSchoolsRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("commaSeparatedIds")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Invalid input format. Ids should be seperated by comma.")));
		verifyZeroInteractions(schoolServiceMock);
	}

	@Test
	public void delete_selected_school_should_return_validation_errors_for_invalid_empty_input() throws Exception {
		DeleteSchoolsRequestObject deleteSchoolsRequestObject = new DeleteSchoolsRequestObject();
		getMockMvc()
				.perform(delete(SchoolControllerUnitTestUtil.DELETE_SCHOOLS)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(deleteSchoolsRequestObject)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.fieldErrors", hasSize(1)))
				.andExpect(jsonPath("$.fieldErrors[*].field", containsInAnyOrder("commaSeparatedIds")))
				.andExpect(jsonPath("$.fieldErrors[*].message",
						containsInAnyOrder("Please provide at least one id to delete.")));
		verifyZeroInteractions(schoolServiceMock);
	}

	@Test
	public void delete_selected_school_should_return_success_message_for_valid_input() throws Exception {
		final String commaSeparatedIds = "20,10";
		DeleteSchoolsRequestObject deleteSchoolsRequestObject = new DeleteSchoolsRequestObject();
		deleteSchoolsRequestObject.setCommaSeparatedIds(commaSeparatedIds);
		doNothing().when(schoolServiceMock).delete(deleteSchoolsRequestObject);
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"Schools have been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUnitTestUtil.DELETE_SCHOOLS)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8).content(
								AttendanceAppUnitTestUtil.convertObjectToJsonBytes(deleteSchoolsRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).delete(deleteSchoolsRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

}
