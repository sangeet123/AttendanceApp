package attendanceapp.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import attendanceapp.dao.StaffDao;
import attendanceapp.exceptions.DuplicateSubjectShortNameException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Staff;
import attendanceapp.model.Subject;

public class StaffDaoImpl implements StaffDao {

	static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where short_name= :shortname and school.id= :schoolId";
	static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId";
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

	private void validateUserName(String username, long schoolId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST)
					.setParameter("username", username).setParameter("schoolId", schoolId);
			if (query.uniqueResult() != null) {
				throw new DuplicateSubjectShortNameException();
			}
		} finally {
			closeSession();
		}
	}

	private void validateShortName(String shortName, long schoolId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST)
					.setParameter("shortName", shortName).setParameter("schoolId", schoolId);
			if (query.uniqueResult() != null) {
				throw new DuplicateSubjectShortNameException();
			}
		} finally {
			closeSession();
		}

	}

	private void validateStaff(Staff staff) {
		try {
			long schoolId = staff.getSchool().getId();
			validateUserName(staff.getUsername(), schoolId);
			validateShortName(staff.getShortName(), schoolId);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		}
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
