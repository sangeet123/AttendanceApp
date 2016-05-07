package attendanceapp.integrationtest.schoolcontroller.create;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.SchoolControllerTestConfigurerIT;
import attendanceapp.integrationtest.utils.SchoolControllerUtilIT;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;

public class SchoolControllercreateIT extends SchoolControllerTestConfigurerIT {

	@Test()
	public void create_valid_school() throws Exception {
		SchoolCreateRequestObject schoolRequestObject = SchoolControllerUtilIT.getSchoolRequestObject("testschool15",
				"Rujesh1@", "Test School", "testemail@email.com", "2453469123");
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"School created sucessfully.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerUtilIT.CREATESCHOOL).header("Authorization", basicDigestHeaderValue)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void creating_school_that_already_exist() throws Exception {
		String schoolThatExistInDatabase = "Test School";
		SchoolCreateRequestObject schoolRequestObject = SchoolControllerUtilIT.getSchoolRequestObject("testschool25",
				"Rujesh1@", schoolThatExistInDatabase, "testemail@email.com", "2453469123");
		final String responseJsonString = "{\"statusCode\":3,\"messages\":[\"School with given name already exists. Please enter different name.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerUtilIT.CREATESCHOOL).header("Authorization", basicDigestHeaderValue)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void creating_school_for_which_user_already_exist() throws Exception {
		String userThatExistInDatabase = "testschool15";
		SchoolCreateRequestObject schoolRequestObject = SchoolControllerUtilIT.getSchoolRequestObject(
				userThatExistInDatabase, "Rujesh1@", "Test School2", "testemail@email.com", "2453469123");
		final String responseJsonString = "{\"statusCode\":3,\"messages\":[\"User with given name already exists. Please try with different name.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerUtilIT.CREATESCHOOL).header("Authorization", basicDigestHeaderValue)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

}
