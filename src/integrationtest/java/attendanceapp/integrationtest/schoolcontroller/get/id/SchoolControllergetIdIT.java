package attendanceapp.integrationtest.schoolcontroller.get.id;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.SchoolControllerTestConfigurerIT;
import attendanceapp.integrationtest.utils.SchoolControllerUtilIT;

public class SchoolControllergetIdIT extends SchoolControllerTestConfigurerIT {
	@Test()
	public void get_school_with_valid_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		getMockMvc()
				.perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, validSchoolId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("test1")))
				.andExpect(jsonPath("$.telephone", is("123456789")))
				.andExpect(jsonPath("$.email", is("test1@email.com")));
	}

	@Test()
	public void get_school_with_valid_id_10_that_exist() throws Exception {
		final long validSchoolId = 10L;
		getMockMvc()
				.perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, validSchoolId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isOk()).andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(10))).andExpect(jsonPath("$.name", is("test10")))
				.andExpect(jsonPath("$.telephone", is("234567891")))
				.andExpect(jsonPath("$.email", is("test10@email.com")));
	}

	@Test()
	public void get_school_with_valid_id_15_that_does_not_exist() throws Exception {
		final long validIdThatDoesNotExist = 15L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"School does not exist.\"]}";
		getMockMvc()
				.perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, validIdThatDoesNotExist).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_school_with_invalid_id_negative_1() throws Exception {
		final long invalidId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"School does not exist.\"]}";
		getMockMvc()
				.perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, invalidId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_school_with_invalid_id_one() throws Exception {
		final String invalidStringId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"messages\":[\"Resource does not exist.\"]}";
		getMockMvc()
				.perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, invalidStringId).header("Authorization",
						basicDigestHeaderValue))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}
}
