package attendanceapp.integrationtest.schoolcontroller.delete;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import attendanceapp.controller.SchoolController;
import attendanceapp.controllerimpl.SchoolControllerImpl;
import attendanceapp.integrationtest.admin.constants.SchoolControllerConstantsIT;
import attendanceapp.integrationtest.common.util.AttendanceAppUtilIT;
import attendanceapp.integrationtest.common.util.TestConfigurerIT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SchoolControllerdeleteIT extends TestConfigurerIT {

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
	public void delete_school_with_valid_id_1_that_exist() throws Exception {
		final long validSchoolId = 1L;
		getMockMvc().perform(delete(SchoolControllerConstantsIT.DELETESCHOOL, validSchoolId)).andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)).andExpect(
						content().string("{\"statusCode\":1,\"message\":[\"School has been deleted successfully.\"]}"));
	}

	@Test()
	public void delete_school_with_valid_id_10_that_exist() throws Exception {
		final long validSchoolId = 10L;
		getMockMvc().perform(delete(SchoolControllerConstantsIT.DELETESCHOOL, validSchoolId)).andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)).andExpect(
						content().string("{\"statusCode\":1,\"message\":[\"School has been deleted successfully.\"]}"));
	}

	@Test()
	public void delete_school_with_valid_id_15_that_does_not_exist() throws Exception {
		final long validIdThatDoesNotExist = 15L;
		getMockMvc().perform(delete(SchoolControllerConstantsIT.DELETESCHOOL, validIdThatDoesNotExist))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string("{\"statusCode\":2,\"message\":[\"School does not exist.\"]}"));

	}

	@Test()
	public void delete_school_with_invalid_id_negative_1() throws Exception {
		final long invalidId = -1L;
		getMockMvc().perform(delete(SchoolControllerConstantsIT.DELETESCHOOL, invalidId))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string("{\"statusCode\":2,\"message\":[\"School does not exist.\"]}"));
	}

	@Test()
	public void delete_school_with_invalid_id_one() throws Exception {
		final String invalidStringId = "one";
		getMockMvc().perform(delete(SchoolControllerConstantsIT.DELETESCHOOL, invalidStringId))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8))
				.andExpect(content().string("{\"statusCode\":2,\"message\":[\"Resource does not exist.\"]}"));

	}
}
