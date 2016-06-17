package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.Staff;

public interface StaffDao {
	Staff getStaff(final long schoolId, final long subjectId);

	List<Staff> getStaffs(final long schoolId);

	Staff update(final long schoolId, final Staff staff);

	void delete(final long schoolId, final long staffId);

	void delete(final long schoolId, final String ids);

	void create(final Staff staff);
}