package attendanceapp.controllerimpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import attendanceapp.services.StaffService;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@Controller()
@RequestMapping(StaffRestControllerConstants.ROOT)
public class StaffControllerImpl implements StaffController {

	@Autowired()
	StaffService staffService;

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.GET_STAFF, method = RequestMethod.GET)
	@ResponseBody()
	public StaffResponseObject getStaff(@PathVariable() long schoolId, @PathVariable() long staffId) {
		return staffService.getStaff(schoolId, staffId);
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.GET_STAFF_LIST, method = RequestMethod.GET)
	@ResponseBody()
	public List<StaffResponseObject> getStaffList(@PathVariable() long schoolId) {
		return staffService.getStaffList(schoolId);
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.DELETE_STAFF, method = RequestMethod.DELETE)
	@ResponseBody()
	public Status delete(@PathVariable() long schoolId, @PathVariable() long staffId) {
		staffService.delete(schoolId, staffId);
		return new Status(AttendanceAppUtils.messageToList(StaffRestControllerConstants.DELETE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
	}

	@Override()
	@RequestMapping(value = StaffRestControllerConstants.DELETE_STAFF_LIST, method = RequestMethod.DELETE)
	@ResponseBody()
	public Status delete(@PathVariable() long schoolId,
			@Valid() @RequestBody() DeleteStaffsRequestObject deleteStaffsRequestObject) {
		staffService.delete(schoolId, deleteStaffsRequestObject);
		return new Status(AttendanceAppUtils.messageToList(StaffRestControllerConstants.SUBJECTS_DELETE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
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
	public StaffResponseObject update(@PathVariable() long schoolId,
			@Valid() @RequestBody() StaffUpdateRequestObject staffUpdateRequestObject) {
		return staffService.update(schoolId, staffUpdateRequestObject);
	}

}
