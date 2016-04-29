package attendanceapp.daoImpl;

import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import attendanceapp.dao.SchoolDao;
import attendanceapp.exceptions.DuplicateSchoolNameException;
import attendanceapp.exceptions.DuplicateUserNameException;
import attendanceapp.model.Authority;
import attendanceapp.model.School;
import attendanceapp.model.User;
import attendanceapp.model.requestobject.SchoolRequestObject;
import attendanceapp.util.SchoolRestServiceUtils;

@Repository()
public class SchoolDaoImpl implements SchoolDao {

	static final String LOAD_ALL_SCHOOL = "FROM attendanceapp.common.model.School";
	static final String DELETE_ALL_SCHOOL = "delete from schools where id IN %s";

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

	private void createSchool(final SchoolRequestObject schoolRequestObject) {
		try {
			School school = SchoolRestServiceUtils
					.createSchoolFromSchoolResponseObject(schoolRequestObject);
			long schoolId = (long) session.save(school);
			schoolRequestObject.setId(schoolId);
			session.flush();
		} catch (ConstraintViolationException ex) {
			throw new DuplicateSchoolNameException();
		}
	}

	private void createUser(final SchoolRequestObject schoolRequestObject) {
		try {
			School school = SchoolRestServiceUtils
					.createSchoolFromSchoolResponseObject(schoolRequestObject);
			User user = SchoolRestServiceUtils
					.createUserFromSchoolResponseObject(schoolRequestObject);
			user.setSchool(school);
			session.save(user);
			Authority authority = SchoolRestServiceUtils
					.createAuthorityFromSchoolRequestObject(schoolRequestObject);
			authority.setUser(user);
			session.save(authority);
			session.flush();
		} catch (ConstraintViolationException ex) {
			throw new DuplicateUserNameException();
		}
	}

	@Override()
	public School getSchool(final long id) {
		try {
			session = sessionFactory.openSession();
			Optional<School> school = Optional.of((School) session.get(
					School.class, new Long(id)));
			return school.get();
		} finally {
			closeSession();
		}
	}

	@Override()
	public List<School> getSchoolList() {
		try {
			session = sessionFactory.openSession();
			org.hibernate.Query query = session.createQuery(LOAD_ALL_SCHOOL);
			@SuppressWarnings("unchecked")
			final List<School> schools = Collections.checkedList(query.list(),
					School.class);
			return schools;
		} finally {
			closeSession();
		}
	}

	@Override()
	public void update(final School school) {
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Optional<School> schoolToUpdate = Optional.of((School) session.get(
					School.class, new Long(school.getId())));
			copyAttributes(schoolToUpdate.get(), school);
			session.update(schoolToUpdate);
			transaction.commit();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void create(final SchoolRequestObject schoolRequestObject) {
		boolean wasCreateSuccessFull = false;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			createSchool(schoolRequestObject);
			createUser(schoolRequestObject);
			wasCreateSuccessFull = true;
			transaction.commit();
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
			Optional<School> school = Optional.of((School) session.load(
					School.class, new Long(id)));
			session.delete(school.get());
			transaction.commit();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(final String ids) {
		try {
			String deleteAll = String.format(DELETE_ALL_SCHOOL, ids);
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = (Query) session.createQuery(deleteAll);
			query.executeUpdate();
			transaction.commit();
		} finally {
			closeSession();
		}
	}
}
