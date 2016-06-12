package attendanceapp.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import attendanceapp.constants.StaffRestControllerConstants;
import attendanceapp.dao.StaffDao;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Staff;
import attendanceapp.model.Subject;

@Repository()
public class StaffDaoImpl implements StaffDao {

	private static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where short_name= :shortname and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where email= :email and school.id= :schoolId";
	private static final String SCHOOLID = "schoolId";
	private final Logger logger = LoggerFactory.getLogger(StaffDaoImpl.class);

	@Autowired()
	SessionFactory sessionFactory;

	Session session = null;
	Transaction transaction = null;

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	private void validateUserName(Staff staff, long schoolId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST)
					.setParameter("username", staff.getUsername()).setParameter(SCHOOLID, schoolId);
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

	private void validateShortName(Staff staff, long schoolId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST)
					.setParameter("shortname", staff.getShortName()).setParameter(SCHOOLID, schoolId);
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

	private void validateEmail(Staff staff, long schoolId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST)
					.setParameter("email", staff.getEmail()).setParameter(SCHOOLID, schoolId);
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

	private void validateStaff(Staff staff) {
		long schoolId = staff.getSchool().getId();
		validateUserName(staff, schoolId);
		validateShortName(staff, schoolId);
		validateEmail(staff, schoolId);
	}

	@Override()
	public Subject getSubject(long schoolId, long subjectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public List<Staff> getSubjects(long schoolId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public void update(long schoolId, Staff staff) {
		// TODO Auto-generated method stub

	}

	@Override()
	public void delete(long schoolId, long staffId) {
		// TODO Auto-generated method stub

	}

	@Override()
	public void delete(long schoolId, String ids) {
		// TODO Auto-generated method stub

	}

	@Override()
	public void create(Staff staff) {
		validateStaff(staff);
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(staff);
			transaction.commit();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}

	}

}
