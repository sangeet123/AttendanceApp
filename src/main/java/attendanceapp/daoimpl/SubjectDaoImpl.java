package attendanceapp.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import attendanceapp.dao.SubjectDao;
import attendanceapp.exceptions.SubjectNotFoundException;
import attendanceapp.model.Subject;

@Repository()
public class SubjectDaoImpl implements SubjectDao {

	@Autowired()
	SessionFactory sessionFactory;

	Session session = null;
	Transaction transaction = null;

	private void closeSession() {
		if (session != null) {
			session.close();
		}
	}

	@Override()
	public Subject getSubject(long schoolId, long subjectId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Subject.class);
			criteria.add(Restrictions.eq("school.id", schoolId)).add(Restrictions.eq("id", subjectId));
			@SuppressWarnings("rawtypes")
			List subjects = criteria.list();
			if (subjects.isEmpty()) {
				throw new SubjectNotFoundException();
			}
			return (Subject) subjects.get(0);
		} finally {
			closeSession();
		}

	}

	@SuppressWarnings("unchecked")
	@Override()
	public List<Subject> getSubjects(long schoolId) {
		try {
			session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Subject.class);
			criteria.add(Restrictions.eq("school.id", schoolId));
			return criteria.list();
		} finally {
			closeSession();
		}
	}

	@Override()
	public void update(long schoolId, Subject subject) {
		// TODO Auto-generated method stub

	}

	@Override()
	public void delete(long schoolId, long subjectId) {
		// TODO Auto-generated method stub

	}

	@Override()
	public void delete(long schoolId, String ids) {
		// TODO Auto-generated method stub

	}

	@Override()
	public void create(Subject subject) {
		// TODO Auto-generated method stub

	}

}
