package attendanceapp.dao.validator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import attendanceapp.constants.StaffRestControllerConstants;
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
	private static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Subject where short_name= :shortname and school.id= :schoolId and id!= :staffId";
	private static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId and id!= :staffId";
	private static final String SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where email= :email and school.id= :schoolId and id!= :staffId";
	private static final String SCHOOLID = "schoolId";
	private static final String STAFFID = "staffId";

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	private void validateUserName(final long schoolId, final String username, final long staffId,
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
			if (query.uniqueResult() != null) {
				throw new ConflictException(StaffRestControllerConstants.DUPLICATE_STAFF_USERNAME);
			}
		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	private void validateShortName(final long schoolId, final String shortName, final long staffId,
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
			if (query.uniqueResult() != null) {
				throw new ConflictException(StaffRestControllerConstants.DUPLICATE_STAFF_SHORT_NAME);
			}
		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	private void validateEmail(final long schoolId, final String email, final long staffId,
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
			if (query.uniqueResult() != null) {
				throw new ConflictException(StaffRestControllerConstants.DUPLICATE_STAFF_EMAIL);
			}
		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	public void validateStaff(final Staff staff) {
		long schoolId = staff.getSchool().getId();
		validateUserName(schoolId, staff.getUsername(), 0, true);
		validateShortName(schoolId, staff.getShortName(), 0, true);
		validateEmail(schoolId, staff.getEmail(), 0, true);
	}

	public void validateStaffUpdateRequestObject(final long schoolId, final StaffUpdateRequestObject request) {
		validateShortName(schoolId, request.getShortName(), 0, false);
		validateEmail(schoolId, request.getEmail(), 0, false);
	}

}
