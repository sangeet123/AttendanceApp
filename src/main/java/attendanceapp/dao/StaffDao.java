package attendanceapp.dao;

import java.util.List;

import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;

public interface StaffDao {
	Staff getStaff(final long schoolId, final long subjectId);

	List<Staff> getStaffs(final long schoolId);

	Staff update(final long schoolId, final StaffUpdateRequestObject staff);

	void delete(final long schoolId, final long staffId);

	void delete(final long schoolId, final String ids);

	void create(final Staff staff);
}