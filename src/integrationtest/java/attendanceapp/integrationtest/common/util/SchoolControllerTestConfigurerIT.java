package attendanceapp.integrationtest.common.util;

import java.sql.SQLException;

import javax.servlet.Filter;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import attendanceapp.controller.SchoolController;
import attendanceapp.controllerimpl.SchoolControllerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:db-configuration-it.xml", "classpath*:mvc-dispatcher-servlet-it.xml" })
@WebAppConfiguration()
public abstract class SchoolControllerTestConfigurerIT {

	public static final String insertSchoolQuerySQLScriptFilePath = "it-insert-school-queries.sql";
	public static final String clearSchoolQuerySQLScriptFilePath = "it-delete-school-queries.sql";
	public boolean isSettedUp = false;
	public static final String basicDigestHeaderValue = "Basic "
			+ new String(Base64.encode(("admin:password").getBytes()));

	@Autowired()
	private WebApplicationContext ctx;

	@Autowired()
	private Filter springSecurityFilterChain;

	private MockMvc mockMvc;

	@Before()
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(springSecurityFilterChain).build();
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
	@EnableWebMvcSecurity()
	@EnableWebMvc()
	static class Config extends WebSecurityConfigurerAdapter {

		@Autowired()
		private BasicDataSource basicDataSource;

		@Override()
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/school/**").hasRole("ADMIN").anyRequest().authenticated().and()
					.httpBasic().and().csrf().disable();
		}

		@Autowired()
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(basicDataSource).passwordEncoder(new BCryptPasswordEncoder(11));
		}

		@Bean()
		public SchoolController schoolController() {
			return new SchoolControllerImpl();
		}
	}

	public MockMvc getMockMvc() {
		return this.mockMvc;
	}

	@AfterClass
	public static void tearDown() {
		try {
			AttendanceAppUtilIT.mysqlScriptRunner(clearSchoolQuerySQLScriptFilePath);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
