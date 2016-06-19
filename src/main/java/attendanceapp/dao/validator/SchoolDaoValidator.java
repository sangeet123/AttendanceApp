package attendanceapp.dao.validator;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.UnknownException;

@Component()
public class SchoolDaoValidator {

	@Autowired()
	private SessionFactory sessionFactory;
	private final Logger logger = LoggerFactory.getLogger(StaffDaoValidator.class);
	private Session session = null;

	private static final String SELECT_SCHOOL_BY_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.School where name= :name";
	private static final String SELECT_SCHOOL_BY_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.School where name= :name and id!= :schoolId";
	private static final String SELECT_USER_BY_USER_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.User where username= :username";
	public static final String INVALID_USERNAME = "user.username";
	public static final String INVALID_NAME = "school.name";

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	private boolean isValidUserName(final String username) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_USER_BY_USER_NAME_IF_ALREADY_EXIST).setParameter("username",
					username);
			return query.uniqueResult() == null;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	private boolean isValidSchoolName(final String name, final long schoolId, final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_SCHOOL_BY_NAME_IF_ALREADY_EXIST).setParameter("name", name);
			} else {
				query = session.createQuery(SELECT_SCHOOL_BY_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("name", name).setParameter("schoolId", schoolId);
			}
			return query.uniqueResult() == null;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	public void throwConflictException(final boolean isUserNameValid, final boolean isSchoolNameValid) {
		Set<String> fields = new HashSet<>();
		if (!isUserNameValid) {
			fields.add(INVALID_USERNAME);
		}

		if (!isSchoolNameValid) {
			fields.add(INVALID_NAME);
		}

		throw new ConflictException(fields);
	}

	public void validateRequest(final String name, final String username) {
		boolean isUserNameValid = isValidUserName(username);
		boolean isSchoolNameValid = isValidSchoolName(name, 0, true);

		if (!isUserNameValid || !isSchoolNameValid) {
			throwConflictException(isUserNameValid, isSchoolNameValid);
		}
	}

	public void validateSchoolName(final String name, final long schoolId) {
		if (!isValidSchoolName(name, schoolId, false)) {
			throwConflictException(true, false);
		}
	}

}
