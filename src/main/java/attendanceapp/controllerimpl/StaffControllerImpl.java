package attendanceapp.controllerimpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import attendanceapp.constants.StaffRestControllerConstants;
import attendanceapp.controller.StaffController;
import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteStaffsRequestObject;
import attendanceapp.model.requestobject.StaffCreateRequestObject;
import attendanceapp.model.requestobject.StaffUpdateRequestObject;
import attendanceapp.model.responseobject.StaffResponseObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.services.StaffService;

public class StaffControllerImpl implements StaffController {

	@Autowired()
	StaffService staffService;

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.GET_STAFF, method = RequestMethod.GET)
	@ResponseBody()
	public SubjectResponseObject getStaff(long schoolId, long staffId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.GET_STAFF_LIST, method = RequestMethod.GET)
	@ResponseBody()
	public List<StaffResponseObject> getStaffList(long schoolId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.DELETE_STAFF_LIST, method = RequestMethod.DELETE)
	@ResponseBody()
	public Status delete(long schoolId, long staffId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.DELETE_STAFF, method = RequestMethod.DELETE)
	@ResponseBody()
	public Status delete(long schoolId, DeleteStaffsRequestObject deleteStaffsRequestObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.CREATE_STAFF, method = RequestMethod.POST)
	@ResponseBody()
	public StaffResponseObject create(@PathVariable() long schoolId,
			@Valid() @RequestBody() StaffCreateRequestObject staffCreateRequestObject) {
		return staffService.create(schoolId, staffCreateRequestObject);
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.UPDATE_STAFF, method = RequestMethod.PUT)
	@ResponseBody()
	public StaffResponseObject update(long schoolId, StaffUpdateRequestObject staffUpdateRequestObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
