package attendancepp.integrationtest.schoolcontroller.create;

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
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.unitest.common.util.AttendanceAppUnitTestUtil;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SchoolControllercreateIT extends TestConfigurerIT {

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
	public void create_valid_school() throws Exception {
		SchoolCreateRequestObject schoolRequestObject = getSchoolRequestObject("testschool15", "Rujesh1@", "Test School",
				"testemail@email.com", "2453469123");
		final String responseJsonString = "{\"statusCode\":1,\"message\":[\"School created sucessfully.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerConstantsIT.CREATESCHOOL)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void creating_school_that_already_exist() throws Exception {
		String schoolThatExistInDatabase = "Test School";
		SchoolCreateRequestObject schoolRequestObject = getSchoolRequestObject("testschool25", "Rujesh1@",
				schoolThatExistInDatabase, "testemail@email.com", "2453469123");
		final String responseJsonString = "{\"statusCode\":3,\"message\":[\"School with given name already exists. Please try with different name.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerConstantsIT.CREATESCHOOL)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	@Test()
	public void creating_school_for_which_user_already_exist() throws Exception {
		String userThatExistInDatabase = "testschool15";
		SchoolCreateRequestObject schoolRequestObject = getSchoolRequestObject(userThatExistInDatabase, "Rujesh1@",
				"Test School2", "testemail@email.com", "2453469123");
		final String responseJsonString = "{\"statusCode\":3,\"message\":[\"User with given name already exists. Please try with different name.\"]}";
		getMockMvc()
				.perform(post(SchoolControllerConstantsIT.CREATESCHOOL)
						.contentType(AttendanceAppUtilIT.APPLICATION_JSON_UTF8)
						.content(AttendanceAppUtilIT.convertObjectToJsonBytes(schoolRequestObject)))
				.andExpect(status().isConflict())
				.andExpect(content().contentType(AttendanceAppUnitTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string(responseJsonString));
	}

	private SchoolCreateRequestObject getSchoolRequestObject(String username, String password, String name, String email,
			String telephone) {
		SchoolCreateRequestObject schoolRequestObject = new SchoolCreateRequestObject();
		schoolRequestObject.setUsername(username);
		schoolRequestObject.setPassword(password);
		schoolRequestObject.setName(name);
		schoolRequestObject.setEmail(email);
		schoolRequestObject.setTelephone(telephone);
		return schoolRequestObject;
	}

}
