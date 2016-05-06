package attendanceapp.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import attendanceapp.dao.SubjectDao;
import attendanceapp.model.Subject;

@Repository()
public class SubjectDaoImpl implements SubjectDao {

	@Autowired()
	SessionFactory sessionFactory;

	Session session = null;
	Transaction transaction = null;

	@Override()
	public Subject getSubject(long schoolId, long subjectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	public boolean isValidPair(long schoolId, long subjectId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override()
	public List<Subject> getSubjects(long schoolId) {
		// TODO Auto-generated method stub
		return null;
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
