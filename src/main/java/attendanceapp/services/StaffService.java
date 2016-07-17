package attendanceapp.services;

import java.util.List;

import attendanceapp.model.Staff;
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;

/**
 * @author sangeet
 *
 */
public interface StaffService {

	Staff getStaffByUserNameAndPassword(String userName, String password);

	public StaffResponseObject getStaff(final long schoolId, final long staffId);

	List<StaffResponseObject> getStaffList(final long schoolId);

	StaffResponseObject update(final long schoolId, final StaffUpdateRequestObject staffUpdateRequest);

	void delete(final long schoolId, final long staffId);

	StaffResponseObject create(final long schoolId, final StaffCreateRequestObject staffCreateRequestObject);

	void delete(final long schoolId, final DeleteStaffsRequestObject deleteStaffsRequestObject);

}
