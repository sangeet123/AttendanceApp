package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.Subject;

public interface SubjectDao {
	Subject getSubject(final long schoolId, final long subjectId);

	List<Subject> getSubjects(final long schoolId);

	void update(final long schoolId, final Subject subject);

	void delete(final long schoolId, final long subjectId);

	void delete(final long schoolId, final String ids);

	void create(final Subject subject);
}