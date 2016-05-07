package attendanceapp.integrationtest.schoolcontroller.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.SchoolControllerTestConfigurerIT;
import attendanceapp.integrationtest.utils.SchoolControllerUtilIT;

public class SchoolControllerdeleteIT extends SchoolControllerTestConfigurerIT {

	@Test()
	public void delete_school_with_valid_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"School has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, validSchoolId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_valid_id_10_that_exist() throws Exception {
		final long validSchoolId = 10L;
		final String responseJsonString = "{\"statusCode\":1,\"messages\":[\"School has been deleted successfully.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, validSchoolId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_valid_id_15_that_does_not_exist() throws Exception {
		final long validIdThatDoesNotExist = 15L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"School does not exist.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, validIdThatDoesNotExist).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));

	}

	@Test()
	public void delete_school_with_invalid_id_negative_1() throws Exception {
		final long invalidId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"School does not exist.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, invalidId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void delete_school_with_invalid_id_one() throws Exception {
		final String invalidStringId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(delete(SchoolControllerUtilIT.DELETESCHOOL, invalidStringId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));

	}
}
