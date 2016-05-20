package attendanceapp.controller;

import java.util.List;

import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.model.responseobject.SubjectResponseObject;

public interface StaffController {

	public SubjectResponseObject getStaff(final long schoolId, final long staffId);

	public List<StaffResponseObject> getStaffList(final long schoolId);

	public Status delete(final long schoolId, final long staffId);

	public Status delete(final long schoolId, final DeleteStaffsRequestObject deleteStaffsRequestObject);

	public StaffResponseObject create(final long schoolId, final StaffCreateRequestObject staffCreateRequestObject);

	public StaffResponseObject update(final long schoolId, final StaffUpdateRequestObject staffUpdateRequestObject);

}
