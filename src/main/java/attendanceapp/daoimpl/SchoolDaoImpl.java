package attendanceapp.daoimpl;

import java.util.Collections;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import attendanceapp.constants.Constant;
import attendanceapp.dao.SchoolDao;
import attendanceapp.dao.validator.SchoolDaoValidator;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Authority;
import attendanceapp.model.School;
import attendanceapp.model.User;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.services.util.SchoolServiceUtils;

@Repository()
public class SchoolDaoImpl implements SchoolDao {

	static final String LOAD_ALL_SCHOOL = "FROM attendanceapp.model.School";
	static final String DELETE_SELECTED_SCHOOL = "CALL delete_selected_schools(:ids)";
	private final Logger logger = LoggerFactory.getLogger(SchoolDaoImpl.class);

	@Autowired()
	private SessionFactory sessionFactory;

	@Autowired()
	private SchoolDaoValidator validator;

	private Session session = null;
	private Transaction transaction = null;

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
		School school = SchoolServiceUtils.createSchoolFromSchoolResponseObject(schoolRequestObject);
		long schoolId = (long) session.save(school);
		schoolRequestObject.setId(schoolId);
		session.flush();
	}

	private void createUser(final SchoolCreateRequestObject schoolRequestObject) {
		School school = SchoolServiceUtils.createSchoolFromSchoolResponseObject(schoolRequestObject);
		User user = SchoolServiceUtils.createUserFromSchoolResponseObject(schoolRequestObject);
		user.setSchool(school);
		session.save(user);
		Authority authority = SchoolServiceUtils.createAuthorityFromSchoolRequestObject(schoolRequestObject);
		authority.setUser(user);
		session.save(authority);
		session.flush();
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
	public School update(final SchoolUpdateRequestObject request) {
		School schoolToUpdate = this.getSchool(request.getId());
		validator.validateSchoolName(request.getName(), request.getId());
		SchoolServiceUtils.copyAttributes(schoolToUpdate, request);
		try {
			session = sessionFactory.openSession();
			session.update(schoolToUpdate);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}
		return schoolToUpdate;
	}

	@Override()
	public void create(final SchoolCreateRequestObject request) {
		validator.validateRequest(request.getName(), request.getUsername());
		boolean wasCreateSuccessFull = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			createSchool(request);
			createUser(request);
			wasCreateSuccessFull = true;
			transaction.commit();
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
		School schoolToDelete = this.getSchool(id);
		try {
			session = sessionFactory.openSession();
			session.delete(schoolToDelete);
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
