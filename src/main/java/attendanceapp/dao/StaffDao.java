package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.Staff;
import attendanceapp.model.Subject;

public interface StaffDao {
	Subject getSubject(final long schoolId, final long subjectId);

	List<Staff> getSubjects(final long schoolId);

	void update(final long schoolId, final Staff staff);

	void delete(final long schoolId, final long staffId);

	void delete(final long schoolId, final String ids);

	void create(final Staff staff);
}