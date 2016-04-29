package attendanceapp.integrationtest.common.util;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:mvc-dispatcher-servlet-it.xml",
		"classpath*:db-configuration-it.xml" })
@WebAppConfiguration()
public abstract class TestConfigurerIT {

	@Autowired()
	private WebApplicationContext ctx;

	private MockMvc mockMvc;

	@Before()
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

	}

	public MockMvc getMockMvc() {
		return this.mockMvc;
	}

}
