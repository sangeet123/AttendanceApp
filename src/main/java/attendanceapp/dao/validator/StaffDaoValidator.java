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
import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;

@Component()
public class StaffDaoValidator {
	@Autowired()
	private SessionFactory sessionFactory;
	private final Logger logger = LoggerFactory.getLogger(StaffDaoValidator.class);
	private Session session = null;

	private static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where short_name= :shortname and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where email= :email and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where short_name= :shortname and school.id= :schoolId and id!= :staffId";
	private static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId and id!= :staffId";
	private static final String SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where email= :email and school.id= :schoolId and id!= :staffId";
	private static final String SCHOOLID = "schoolId";
	private static final String STAFFID = "staffId";
	public static final String INVALID_USERNAME = "staff.username";
	public static final String INVALID_EMAIL = "staff.email";
	public static final String INVALID_SHORTNAME = "staff.shortname";

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	private boolean isValidUserName(final long schoolId, final String username, final long staffId,
			final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST)
						.setParameter("username", username).setParameter(SCHOOLID, schoolId);
			} else {
				query = session.createQuery(SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("username", username).setParameter(SCHOOLID, schoolId)
						.setParameter(STAFFID, staffId);
			}
			return query.uniqueResult() == null;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	private boolean isValidShortName(final long schoolId, final String shortName, final long staffId,
			final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST)
						.setParameter("shortname", shortName).setParameter(SCHOOLID, schoolId);
			} else {
				query = session.createQuery(SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("shortname", shortName).setParameter(SCHOOLID, schoolId)
						.setParameter(STAFFID, staffId);
			}
			return query.uniqueResult() == null;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	private boolean isValidEmail(final long schoolId, final String email, final long staffId,
			final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST).setParameter("email", email)
						.setParameter(SCHOOLID, schoolId);
			} else {
				query = session.createQuery(SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("email", email).setParameter(SCHOOLID, schoolId).setParameter(STAFFID, staffId);
			}
			return query.uniqueResult() == null;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	public Set<String> createFieldsError(final boolean isUserNameValid, final boolean isShortNameValid,
			final boolean isEmailValid) {
		Set<String> fieldsError = new HashSet<>();
		if (!isUserNameValid) {
			fieldsError.add(INVALID_USERNAME);
		}

		if (!isShortNameValid) {
			fieldsError.add(INVALID_SHORTNAME);
		}

		if (!isEmailValid) {
			fieldsError.add(INVALID_EMAIL);
		}
		return fieldsError;
	}

	public void validateStaff(final Staff staff) {
		long schoolId = staff.getSchool().getId();

		final boolean isUserNameValid = isValidUserName(schoolId, staff.getUsername(), 0, true);
		final boolean isShortNameValid = isValidShortName(schoolId, staff.getShortName(), 0, true);
		final boolean isEmailValid = isValidEmail(schoolId, staff.getEmail(), 0, true);

		if (!isUserNameValid || !isShortNameValid || !isEmailValid) {
			throw new ConflictException(createFieldsError(isUserNameValid, isShortNameValid, isEmailValid));
		}
	}

	public void validateStaffUpdateRequestObject(final long schoolId, final StaffUpdateRequestObject request) {
		final boolean isShortNameValid = isValidShortName(schoolId, request.getShortName(), request.getId(), false);
		final boolean isEmailValid = isValidEmail(schoolId, request.getEmail(), request.getId(), false);
		if (!isShortNameValid || !isEmailValid) {
			throw new ConflictException(createFieldsError(true, isShortNameValid, isEmailValid));
		}
	}

}
