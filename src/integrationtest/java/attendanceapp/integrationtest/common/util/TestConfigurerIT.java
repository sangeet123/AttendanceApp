package attendanceapp.integrationtest.common.util;

import javax.servlet.Filter;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:db-configuration-it.xml", "classpath*:mvc-dispatcher-servlet-it.xml" })
@WebAppConfiguration()
public abstract class TestConfigurerIT {

	/*
	 * Finally decided to keep only the global configuration parameters here.
	 * Configuration of other things sql script file has been moved to the
	 * respective classes as they could be changed for each action method.
	 */

	@Autowired()
	private WebApplicationContext ctx;

	@Autowired()
	private Filter springSecurityFilterChain;

	private MockMvc mockMvc;

	@Before()
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(springSecurityFilterChain).build();
	}

	@Configuration()
	@EnableWebMvcSecurity()
	@EnableWebMvc()
	static class Config extends WebSecurityConfigurerAdapter {

		@Autowired()
		private BasicDataSource basicDataSource;

		@Override()
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/school/**")
					.hasRole("SCHOOL_ADMIN").anyRequest().authenticated().and().httpBasic().and().csrf().disable();
		}

		@Autowired()
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.jdbcAuthentication().dataSource(basicDataSource).passwordEncoder(new BCryptPasswordEncoder(11));
		}
	}

	public MockMvc getMockMvc() {
		return this.mockMvc;
	}
}
