package attendanceapp.daoimpl;

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
import attendanceapp.constants.SubjectRestControllerConstants;
import attendanceapp.dao.SubjectDao;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.model.Subject;

@Repository()
public class SubjectDaoImpl implements SubjectDao {

	private final Logger logger = LoggerFactory.getLogger(SubjectDaoImpl.class);

	private static final String DELETE_SUBJECT_BY_ID = "delete attendanceapp.model.Subject where id= :subjectId and school.id= :schoolId";
	private static final String DELETE_SUBJECTS_BY_IDS = "delete attendanceapp.model.Subject where id in (:subjectIds) and school.id= :schoolId";
	private static final String SELECT_SUBJECT_BY_SHORT_NAME = "from attendanceapp.model.Subject where short_name= :shortname and school.id= :schoolId";
	private static final String SELECT_SUBJECT_BY_SHORT_NAME_IF_ALREADY_EXIST = "from attendanceapp.model.Subject where short_name= :shortname and school.id= :schoolId and id!= :subjectId";

	@Autowired()
	SessionFactory sessionFactory;

	Session session = null;
	Transaction transaction = null;

	private void closeSession() {
		if (session != null && session.isConnected()) {
			session.close();
		}
	}

	private Subject findSubject(final long schoolId, final long subjectId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Subject.class);
			criteria.add(Restrictions.eq("school.id", schoolId)).add(Restrictions.eq("id", subjectId));
			@SuppressWarnings("rawtypes")
			List subjects = criteria.list();
			if (subjects.isEmpty()) {
				throw new NotFoundException(Constant.RESOURSE_DOES_NOT_EXIST);
			}
			return (Subject) subjects.get(0);
		} finally {
			closeSession();
		}
	}

	@Override()
	public Subject getSubject(final long schoolId, final long subjectId) {
		try {
			return findSubject(schoolId, subjectId);
		} catch (NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override()
	public List<Subject> getSubjects(final long schoolId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Subject.class);
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
	public void update(final long schoolId, final Subject subject) {
		try {
			findSubject(schoolId, subject.getId());
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_SUBJECT_BY_SHORT_NAME_IF_ALREADY_EXIST)
					.setParameter("shortname", subject.getShortName())
					.setParameter("schoolId", subject.getSchool().getId()).setParameter("subjectId", subject.getId());
			if (query.uniqueResult() == null) {
				session.save(subject);
			} else {
				throw new ConflictException(SubjectRestControllerConstants.DUPLICATE_SUBJECT_SHORT_NAME);
			}
		} catch (ConflictException | NotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("", ex);
		} finally {
			closeSession();
		}
	}

	@Override()
	public void delete(final long schoolId, final long subjectId) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(DELETE_SUBJECT_BY_ID).setParameter("subjectId", subjectId)
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
	public void delete(final long schoolId, final String ids) {
		try {
			List<Long> numbers = Stream.of(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
			session = sessionFactory.openSession();
			Query query = session.createQuery(DELETE_SUBJECTS_BY_IDS).setParameter("schoolId", schoolId)
					.setParameterList("subjectIds", numbers);
			query.executeUpdate();
		} catch (Exception ex) {
			logger.error("", ex);
			throw new UnknownException();
		} finally {
			closeSession();
		}

	}

	@Override()
	public void create(final Subject subject) {
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery(SELECT_SUBJECT_BY_SHORT_NAME)
					.setParameter("shortname", subject.getShortName())
					.setParameter("schoolId", subject.getSchool().getId());
			if (query.uniqueResult() == null) {
				session.save(subject);
			} else {
				throw new ConflictException(SubjectRestControllerConstants.DUPLICATE_SUBJECT_SHORT_NAME);
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

}
