package attendanceapp.integrationtest.schoolcontroller.get.id;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import attendanceapp.controller.SchoolController;
import attendanceapp.controllerimpl.SchoolControllerImpl;
import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import attendanceapp.integrationtest.utils.SchoolControllerUtilIT;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

public class SchoolControllergetIdIT extends TestConfigurerIT {

	private static final String insertSchoolQuerySQLScriptFilePath = "it-insert-school-queries.sql";
	private static boolean isSettedUp = false;

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

	@Configuration()
	@EnableWebMvc()
	public static class TestConfiguration {
		@Bean()
		public SchoolController schoolController() {
			return new SchoolControllerImpl();
		}
	}

	@Test()
	public void get_school_with_valid_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		getMockMvc().perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, validSchoolId)).andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("test1")))
				.andExpect(jsonPath("$.telephone", is("123456789")))
				.andExpect(jsonPath("$.email", is("test1@email.com")));
	}

	@Test()
	public void get_school_with_valid_id_10_that_exist() throws Exception {
		final long validSchoolId = 10L;
		getMockMvc().perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, validSchoolId)).andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(10))).andExpect(jsonPath("$.name", is("test10")))
				.andExpect(jsonPath("$.telephone", is("234567891")))
				.andExpect(jsonPath("$.email", is("test10@email.com")));
	}

	@Test()
	public void get_school_with_valid_id_15_that_does_not_exist() throws Exception {
		final long validIdThatDoesNotExist = 15L;
		final String responseJsonString = "{\"statusCode\":2,\"message\":[\"School does not exist.\"]}";
		getMockMvc().perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, validIdThatDoesNotExist))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_school_with_invalid_id_negative_1() throws Exception {
		final long invalidId = -1L;
		final String responseJsonString = "{\"statusCode\":2,\"message\":[\"School does not exist.\"]}";
		getMockMvc().perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, invalidId)).andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void get_school_with_invalid_id_one() throws Exception {
		final String invalidStringId = "one";
		final String responseJsonString = "{\"statusCode\":2,\"message\":[\"Resource does not exist.\"]}";
		getMockMvc().perform(get(SchoolControllerUtilIT.GETSCHOOLWITHID, invalidStringId))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}
}
