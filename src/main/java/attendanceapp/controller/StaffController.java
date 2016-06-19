package attendanceapp.controller;

import java.util.List;

import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;

public interface StaffController {

	StaffResponseObject getStaff(final long schoolId, final long staffId);

	List<StaffResponseObject> getStaffList(final long schoolId);

	Status delete(final long schoolId, final long staffId);

	Status delete(final long schoolId, final DeleteStaffsRequestObject deleteStaffsRequestObject);

	StaffResponseObject create(final long schoolId, final StaffCreateRequestObject staffCreateRequestObject);

	StaffResponseObject update(final long schoolId, final StaffUpdateRequestObject staffUpdateRequestObject);

}
