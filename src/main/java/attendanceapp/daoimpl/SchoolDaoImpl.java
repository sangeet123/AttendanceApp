package attendanceapp.daoimpl;

import java.util.Collections;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import attendanceapp.constants.Constant;
import attendanceapp.constants.SchoolRestControllerConstants;
import attendanceapp.dao.SchoolDao;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Authority;
import attendanceapp.model.School;
import attendanceapp.model.User;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.services.util.SchoolServiceUtils;

@Repository()
public class SchoolDaoImpl implements SchoolDao {

	static final String LOAD_ALL_SCHOOL = "FROM attendanceapp.model.School";
	static final String DELETE_SELECTED_SCHOOL = "CALL delete_selected_schools(:ids)";
	private final Logger logger = LoggerFactory.getLogger(SchoolDaoImpl.class);

	@Autowired()
	SessionFactory sessionFactory;

	Session session = null;
	Transaction transaction = null;

	private void copyAttributes(School to, final School from) {
		to.setEmail(from.getEmail());
		to.setName(from.getName());
		to.setTelephone(from.getTelephone());
	}

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	private void rollBackTransaction() {
		if (transaction != null) {
			transaction.rollback();
		}
	}

	private void createSchool(final SchoolCreateRequestObject schoolRequestObject) {
		try {
			School school = SchoolServiceUtils.createSchoolFromSchoolResponseObject(schoolRequestObject);
			long schoolId = (long) session.save(school);
			schoolRequestObject.setId(schoolId);
			session.flush();
		} catch (ConstraintViolationException ex) {
			logger.error("", ex);
			throw new ConflictException(SchoolRestControllerConstants.DUPLICATE_SCHOOL_NAME);
		}
	}

	private void createUser(final SchoolCreateRequestObject schoolRequestObject) {
		try {
			School school = SchoolServiceUtils.createSchoolFromSchoolResponseObject(schoolRequestObject);
			User user = SchoolServiceUtils.createUserFromSchoolResponseObject(schoolRequestObject);
			user.setSchool(school);
			session.save(user);
			Authority authority = SchoolServiceUtils.createAuthorityFromSchoolRequestObject(schoolRequestObject);
			authority.setUser(user);
			session.save(authority);
			session.flush();
		} catch (ConstraintViolationException ex) {
			logger.error("", ex);
			throw new ConflictException(SchoolRestControllerConstants.CREATE_USER_FAILURE_DUPLICATE_ENTRY);
		}
	}

	@SuppressWarnings("unchecked")
	@Override()
	public List<School> getSchoolList() {
		try {
			session = sessionFactory.openSession();
			org.hibernate.Query query = session.createQuery(LOAD_ALL_SCHOOL);
			return Collections.checkedList(query.list(), School.class);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public School getSchool(final long id) {
		try {
			session = sessionFactory.openSession();
			Optional<School> school = Optional.of((School) session.get(School.class, new Long(id)));
			return school.get();
		} catch (ObjectNotFoundException | NullPointerException ex) {
			logger.error("", ex);
			throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void update(final School school) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Optional<School> schoolToUpdate = Optional.of((School) session.get(School.class, new Long(school.getId())));
			copyAttributes(schoolToUpdate.get(), school);
			session.update(schoolToUpdate.get());
			transaction.commit();
		} catch (ObjectNotFoundException | NullPointerException ex) {
			logger.error("", ex);
			throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
		} catch (ConstraintViolationException ex) {
			logger.error("", ex);
			throw new ConflictException(SchoolRestControllerConstants.DUPLICATE_SCHOOL_NAME);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void create(final SchoolCreateRequestObject schoolRequestObject) {
		boolean wasCreateSuccessFull = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			createSchool(schoolRequestObject);
			createUser(schoolRequestObject);
			wasCreateSuccessFull = true;
			transaction.commit();
		} catch (ConflictException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException(ex.getMessage());
		} finally {
			if (!wasCreateSuccessFull) {
				rollBackTransaction();
			}
			closeSession();
		}
	}

	@Override()
	public void delete(final long id) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Optional<School> school = Optional.of((School) session.load(School.class, new Long(id)));
			session.delete(school.get());
			transaction.commit();
		} catch (ObjectNotFoundException | NullPointerException ex) {
			logger.error("", ex);
			throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(final String ids) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.createSQLQuery(DELETE_SELECTED_SCHOOL).setParameter("ids", ids).executeUpdate();
			transaction.commit();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
	}
}
