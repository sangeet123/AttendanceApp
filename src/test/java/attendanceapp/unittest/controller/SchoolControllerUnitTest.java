package attendanceapp.unittest.controller;

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
import attendanceapp.exceptions.DuplicateSchoolNameException;
import attendanceapp.exceptions.SchoolNotFoundException;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.services.SchoolService;
import attendanceapp.unitest.common.util.AttendanceAppUnitTestUtil;
import attendanceapp.unitest.common.util.UnitTestConfigurer;
import attendanceapp.unittest.constants.SchoolControllerUnitTestConstants;
import attendanceapp.unittest.testdata.SchoolControllerUnitTestData;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

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
	public void getSchool_ShouldReturnAllRegisteredSchools() throws Exception {

		List<SchoolResponseObject> testSchools = SchoolControllerUnitTestData.getTestSchoolList();

		when(schoolServiceMock.getSchoolList()).thenReturn(testSchools);

		ResultActions resultAction = getMockMvc().perform(get(SchoolControllerUnitTestConstants.GETALLSCHOOLS))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(testSchools.size())));

		for (int i = 0; i < testSchools.size(); ++i) {
			String jasonPath = String.format(AttendanceAppUnitTestUtil.JASONLIST, i);
			SchoolResponseObject schoolResponseObject = testSchools.get(i);
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestConstants.ID),
					is((int) schoolResponseObject.getId())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestConstants.NAME),
					is(schoolResponseObject.getName())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestConstants.EMAIL),
					is(schoolResponseObject.getEmail())));
			resultAction.andExpect(jsonPath(jasonPath.concat(SchoolControllerUnitTestConstants.TELEPHONE),
					is(schoolResponseObject.getTelephone())));
		}
		verify(schoolServiceMock, atLeast(1)).getSchoolList();
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void getSchoolById_ShouldReturnSchool_for_valid_id_and_school_that_exist() throws Exception {

		long id = 5;
		List<SchoolResponseObject> schoolsResponseObject = SchoolControllerUnitTestData.getSchool(id);
		SchoolResponseObject returnedSchoolResponseObject = schoolsResponseObject.get(0);
		when(schoolServiceMock.getSchool(id)).thenReturn(returnedSchoolResponseObject);

		getMockMvc().perform(get(SchoolControllerUnitTestConstants.GETSCHOOLWITHID, id)).andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestConstants.ID),
						is((int) returnedSchoolResponseObject.getId())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestConstants.NAME),
						is(returnedSchoolResponseObject.getName())))
				.andExpect(jsonPath(AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestConstants.EMAIL),
						is(returnedSchoolResponseObject.getEmail())))
				.andExpect(jsonPath(
						AttendanceAppUnitTestUtil.JASONUNIT.concat(SchoolControllerUnitTestConstants.TELEPHONE),
						is(returnedSchoolResponseObject.getTelephone())));
		verify(schoolServiceMock, atLeast(1)).getSchool(id);
		verifyNoMoreInteractions(schoolServiceMock);

	}

	@Test()
	public void getSchoolById_ShouldReturnHttpStatusCode404_for_valid_id_but_school_that_does_not_exist()
			throws Exception {
		long validIdThatDoesNotExist = 11L;
		when(schoolServiceMock.getSchool(validIdThatDoesNotExist))
				.thenThrow(new SchoolNotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST));
		final String responseJsonString = "{\"statusCode\":2,\"message\":[\"School does not exist.\"]}";
		getMockMvc().perform(get(SchoolControllerUnitTestConstants.GETSCHOOLWITHID, validIdThatDoesNotExist))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).getSchool(validIdThatDoesNotExist);
		verifyNoMoreInteractions(schoolServiceMock);

	}

	@Test()
	public void getSchoolById_ShouldReturnHttpStatusCode404_for_id_that_is_invalid() throws Exception {
		long invalidSchoolId = -1L;
		when(schoolServiceMock.getSchool(invalidSchoolId))
				.thenThrow(new SchoolNotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST));
		getMockMvc().perform(get(SchoolControllerUnitTestConstants.GETSCHOOLWITHID, invalidSchoolId))
				.andExpect(status().isNotFound());
		verify(schoolServiceMock, atLeast(1)).getSchool(invalidSchoolId);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void deleteSchoolById_ShouldReturnHttpStatusCode404_for_id_that_is_valid_but_does_not_exist()
			throws Exception {
		long validSchoolIdThatDoesNotExist = 15L;
		doThrow(new SchoolNotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST))
				.when(schoolServiceMock).delete(validSchoolIdThatDoesNotExist);
		getMockMvc().perform(delete(SchoolControllerUnitTestConstants.DELETESCHOOL, validSchoolIdThatDoesNotExist))
				.andExpect(status().isNotFound());
		verify(schoolServiceMock, atLeast(1)).delete(validSchoolIdThatDoesNotExist);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void deleteSchoolById_ShouldReturnHttpStatusCode404_for_id_that_is_invalid() throws Exception {
		long invalidSchoolId = -1L;
		doThrow(new SchoolNotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST))
				.when(schoolServiceMock).delete(invalidSchoolId);
		getMockMvc().perform(delete(SchoolControllerUnitTestConstants.DELETESCHOOL, invalidSchoolId))
				.andExpect(status().isNotFound());
		verify(schoolServiceMock, atLeast(1)).delete(invalidSchoolId);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void createSchool_ShouldReturnHttpStatusCode200() throws Exception {
		SchoolCreateRequestObject schoolRequestObject = getSchoolCreateRequestObject("testschool15", "Rujesh1@",
				"Test School", "testemail@email.com", "2453469123");
		doNothing().when(schoolServiceMock).create(schoolRequestObject);
		final String responseJsonString = "{\"statusCode\":1,\"message\":[\"School created sucessfully.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerUnitTestConstants.CREATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).create(schoolRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void createSchool_ShouldReturnValidationError_For_Empty_School_In_English_() throws Exception {
		SchoolCreateRequestObject schoolRequestObject = new SchoolCreateRequestObject();
		getMockMvc()
				.perform(post(SchoolControllerUnitTestConstants.CREATESCHOOL)
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
	public void createSchool_ShouldReturnValidationError_For_InvalidTelephone_InvalidEmail_And_Null_School_Name_In_English_()
			throws Exception {
		SchoolCreateRequestObject schoolRequestObject = getSchoolCreateRequestObject(null, null, null, "abc@", "1234");
		getMockMvc()
				.perform(post(SchoolControllerUnitTestConstants.CREATESCHOOL)
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
	public void updateSchool_ShouldReturnHttpStatusCode200_and_school_update_response_object() throws Exception {
		SchoolUpdateRequestObject schoolUpdateRequestObject = getSchoolUpdateRequestObject(1L, "Test School",
				"testemail@email.com", "2453469123");
		doNothing().when(schoolServiceMock).update(schoolUpdateRequestObject);
		final String responseJsonString = AttendanceAppUnitTestUtil
				.convertObjectToJsonString(schoolUpdateRequestObject);
		getMockMvc()
				.perform(put(SchoolControllerUnitTestConstants.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).update(schoolUpdateRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test()
	public void updateSchool_ShouldReturnValidationError_For_Empty_School_In_English_() throws Exception {
		SchoolUpdateRequestObject schoolUpdateRequestObject = new SchoolUpdateRequestObject();
		getMockMvc()
				.perform(put(SchoolControllerUnitTestConstants.UPDATESCHOOL)
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
	public void updateSchool_ShouldReturnValidationError_For_InvalidTelephone_InvalidEmail_And_Null_School_Name_In_English()
			throws Exception {
		SchoolUpdateRequestObject schoolUpdateRequestObject = new SchoolUpdateRequestObject();
		schoolUpdateRequestObject.setEmail("abc@");
		schoolUpdateRequestObject.setTelephone("1234");

		getMockMvc()
				.perform(put(SchoolControllerUnitTestConstants.UPDATESCHOOL)
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
	public void updateSchool_SchouldReturn_DuplicateSchoolNameException_For_School_that_already_exist()
			throws Exception {
		final String duplicateSchoolName = "Test School";
		SchoolUpdateRequestObject schoolUpdateRequestObject = getSchoolUpdateRequestObject(1L, duplicateSchoolName,
				"testemail@email.com", "2453469123");
		doThrow(new DuplicateSchoolNameException(SchoolRestControllerConstants.DUPLICATE_SCHOOL_NAME))
				.when(schoolServiceMock).update(schoolUpdateRequestObject);
		final String responseJsonString = "{\"statusCode\":3,\"message\":[\"School with given name already exists. Please enter different name.\"]}";
		getMockMvc()
				.perform(put(SchoolControllerUnitTestConstants.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).update(schoolUpdateRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	@Test
	public void updateSchool_SchouldReturn_SchoolNotFoundException_For_School_that_does_not_exist() throws Exception {
		final long nonExistantSchoolId = 10L;
		SchoolUpdateRequestObject schoolUpdateRequestObject = getSchoolUpdateRequestObject(nonExistantSchoolId,
				"Test School", "testemail@email.com", "2453469123");
		doThrow(new SchoolNotFoundException(SchoolRestControllerConstants.SCHOOL_DOES_NOT_EXIST))
				.when(schoolServiceMock).update(schoolUpdateRequestObject);
		final String responseJsonString = "{\"statusCode\":2,\"message\":[\"School does not exist.\"]}";
		getMockMvc()
				.perform(put(SchoolControllerUnitTestConstants.UPDATESCHOOL)
						.contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUnitTestUtil.convertObjectToJsonBytes(schoolUpdateRequestObject)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
		verify(schoolServiceMock, atLeast(1)).update(schoolUpdateRequestObject);
		verifyNoMoreInteractions(schoolServiceMock);
	}

	private SchoolCreateRequestObject getSchoolCreateRequestObject(String username, String password, String name,
			String email, String telephone) {
		SchoolCreateRequestObject schoolRequestObject = new SchoolCreateRequestObject();
		schoolRequestObject.setUsername(username);
		schoolRequestObject.setPassword(password);
		schoolRequestObject.setName(name);
		schoolRequestObject.setEmail(email);
		schoolRequestObject.setTelephone(telephone);
		return schoolRequestObject;
	}

	private SchoolUpdateRequestObject getSchoolUpdateRequestObject(long id, String name, String email,
			String telephone) {
		SchoolUpdateRequestObject schoolUpdateRequestObject = new SchoolUpdateRequestObject();
		schoolUpdateRequestObject.setId(id);
		schoolUpdateRequestObject.setName(name);
		schoolUpdateRequestObject.setEmail(email);
		schoolUpdateRequestObject.setTelephone(telephone);
		return schoolUpdateRequestObject;
	}
}
