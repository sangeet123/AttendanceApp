package attendanceapp.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4186276038886303459L;

	public HibernateAwareObjectMapper() {
		Hibernate4Module hm = new Hibernate4Module();
		// this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		// false);
		registerModule(hm);
	}
}