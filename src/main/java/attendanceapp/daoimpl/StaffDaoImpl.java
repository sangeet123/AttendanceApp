package attendanceapp.daoimpl;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import attendanceapp.constants.Constant;
import attendanceapp.constants.StaffRestControllerConstants;
import attendanceapp.dao.StaffDao;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Staff;

@Repository()
public class StaffDaoImpl implements StaffDao {

	private static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where short_name= :shortname and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST = "from attendanceapp.model.Staff where email= :email and school.id= :schoolId";
	private static final String DELETE_STAFF_BY_ID = "delete attendanceapp.model.Staff where id= :staffId and school.id= :schoolId";
	private static final String DELETE_STAFFS_BY_IDS = "delete attendanceapp.model.Staff where id in (:staffIds) and school.id= :schoolId";
	private static final String SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Subject where short_name= :shortname and school.id= :schoolId and id!= :staffId";
	private static final String SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where user_name= :username and school.id= :schoolId and id!= :staffId";
	private static final String SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID = "from attendanceapp.model.Staff where email= :email and school.id= :schoolId and id!= :staffId";
	private static final String SCHOOLID = "schoolId";
	private static final String STAFFID = "staffId";
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

	private void copyAttributes(final Staff original, final Staff updateObject) {
		original.setComment(updateObject.getComment());
		original.setFirstName(updateObject.getFirstName());
		original.setLastName(updateObject.getLastName());
		original.setComment(updateObject.getComment());
		original.setEmail(updateObject.getEmail());
		original.setRole(updateObject.getShortName());
		original.setUpdatedOn(LocalDateTime.now(Clock.systemUTC()));
		original.setRole(updateObject.getRole());
		original.setShortName(updateObject.getShortName());
		original.setTelephone(updateObject.getTelephone());
	}

	private Staff findStaff(final long schoolId, final long staffId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Staff.class);
			criteria.add(Restrictions.eq("school.id", schoolId)).add(Restrictions.eq("id", staffId));
			@SuppressWarnings("rawtypes")
			List staffs = criteria.list();
			if (staffs.isEmpty()) {
				throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
			}
			return (Staff) staffs.get(0);
		} finally {
			closeSession();
		}
	}

	private void validateUserName(final Staff staff, final long schoolId, final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST)
						.setParameter("username", staff.getUsername()).setParameter(SCHOOLID, schoolId);
			} else {
				query = session.createQuery(SELECT_STAFF_BY_USER_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("username", staff.getUsername()).setParameter(SCHOOLID, schoolId)
						.setParameter(STAFFID, staff.getId());
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

	private void validateShortName(final Staff staff, final long schoolId, final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST)
						.setParameter("shortname", staff.getShortName()).setParameter(SCHOOLID, schoolId);
			} else {
				query = session.createQuery(SELECT_STAFF_BY_SHORT_NAME_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("shortname", staff.getShortName()).setParameter(SCHOOLID, schoolId)
						.setParameter(STAFFID, staff.getId());
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

	private void validateEmail(final Staff staff, final long schoolId, final boolean isCreateOperation) {
		try {
			session = sessionFactory.openSession();
			Query query;
			if (isCreateOperation) {
				query = session.createQuery(SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST)
						.setParameter("email", staff.getEmail()).setParameter(SCHOOLID, schoolId);
			} else {
				query = session.createQuery(SELECT_STAFF_BY_EMAIL_IF_ALREADY_EXIST_EXCEPT_FOR_GIVEN_ID)
						.setParameter("email", staff.getEmail()).setParameter(SCHOOLID, schoolId)
						.setParameter(STAFFID, staff.getId());
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

	private void validateStaff(final Staff staff, final boolean isCreateOperation) {
		long schoolId = staff.getSchool().getId();
		validateUserName(staff, schoolId, isCreateOperation);
		validateShortName(staff, schoolId, isCreateOperation);
		validateEmail(staff, schoolId, isCreateOperation);
	}

	@Override()
	public Staff getStaff(long schoolId, long staffId) {
		try {
			return findStaff(schoolId, staffId);
		} catch (NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override()
	public List<Staff> getStaffs(long schoolId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Staff.class);
			criteria.add(Restrictions.eq("school.id", schoolId));
			return criteria.list();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public Staff update(long schoolId, final Staff staff) {
		validateStaff(staff, false);
		Staff originalStaff = this.getStaff(schoolId, staff.getId());
		copyAttributes(originalStaff, staff);
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.update(originalStaff);
			transaction.commit();
			return originalStaff;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(long schoolId, long staffId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(DELETE_STAFF_BY_ID).setParameter("staffId", staffId)
					.setParameter("schoolId", schoolId);
			if (query.executeUpdate() == 0) {
				throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
			}
		} catch (NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(long schoolId, String ids) {
		try {
			List<Long> numbers = Stream.of(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
			session = sessionFactory.openSession();
			Query query = session.createQuery(DELETE_STAFFS_BY_IDS).setParameter("schoolId", schoolId)
					.setParameterList("staffIds", numbers);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void create(Staff staff) {
		validateStaff(staff, true);
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
