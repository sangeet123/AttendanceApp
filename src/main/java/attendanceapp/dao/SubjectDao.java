package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.Subject;

public interface SubjectDao {
	public Subject getSubject(final long schoolId, final long subjectId);

	public boolean isValidPair(final long schoolId, final long subjectId);

	public List<Subject> getSubjects(final long schoolId);

	public void update(final long schoolId, final Subject subject);

	public void delete(final long schoolId, final long subjectId);

	public void delete(final long schoolId, final String ids);

	void create(final Subject subject);
}