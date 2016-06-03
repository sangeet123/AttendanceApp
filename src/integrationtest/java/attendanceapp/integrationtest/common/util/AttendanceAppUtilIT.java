package attendanceapp.integrationtest.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.http.MediaType;
import com.mysql.jdbc.Connection;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibatis.common.jdbc.ScriptRunner;

public final class AttendanceAppUtilIT {

	private static final String DATABASEPROPERTIESFILE = "it-database.properties";
	private static final String JDBCDRIVERCLASS = "jdbc.driverClassName";
	private static final String JDBCURL = "jdbc.url";
	private static final String JDBCUSERNAME = "jdbc.username";
	private static final String JDBCPASSWORD = "jdbc.password";
	private static Connection con = null;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	// Header for authorization
	public static final String AUTHORIZATION = "Authorization";

	private AttendanceAppUtilIT() throws InstantiationException {
		throw new InstantiationException();
	}

	public static String convertObjectToJsonString(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
		return mapper.writeValueAsString(object);
	}

	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	public static String resourceToString(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
	}

	public static Properties readDatabaseProperties(String fileName) {

		Properties prop = new Properties();
		File file = new File(resourceToString(fileName));
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			prop.load(inputStream);
			return prop;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	public static Connection getDatabaseConnection() throws ClassNotFoundException {
		Properties prop = readDatabaseProperties(DATABASEPROPERTIESFILE);
		Class.forName(prop.getProperty(JDBCDRIVERCLASS));
		try {
			if (con == null) {
				con = (Connection) DriverManager.getConnection(prop.getProperty(JDBCURL),
						prop.getProperty(JDBCUSERNAME), prop.getProperty(JDBCPASSWORD));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void mysqlScriptRunner(String sqlQueryFileName) throws ClassNotFoundException, SQLException {
		Connection con = getDatabaseConnection();
		File file = new File(resourceToString(sqlQueryFileName));
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			ScriptRunner scriptRunner = new ScriptRunner(con, false, false);
			Reader reader = new InputStreamReader(inputStream);
			scriptRunner.runScript(reader);
		} catch (Exception e) {
			System.err.println("Failed to Execute" + sqlQueryFileName + " The error is " + e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
