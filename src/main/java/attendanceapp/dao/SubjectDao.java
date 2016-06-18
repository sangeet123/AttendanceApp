package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.Subject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;

public interface SubjectDao {
	Subject getSubject(final long schoolId, final long subjectId);

	List<Subject> getSubjects(final long schoolId);

	Subject update(final long schoolId, final SubjectUpdateRequestObject subject);

	void delete(final long schoolId, final long subjectId);

	void delete(final long schoolId, final String ids);

	void create(final Subject subject);
}