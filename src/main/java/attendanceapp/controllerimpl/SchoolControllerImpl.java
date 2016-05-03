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

import attendanceapp.constants.SchoolRestControllerConstants;
import attendanceapp.controller.SchoolController;
import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteSelectedSchoolRequestObject;
import attendanceapp.model.requestobject.SchoolCreateRequestObject;
import attendanceapp.model.requestobject.SchoolUpdateRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;
import attendanceapp.services.SchoolService;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@Controller()
@RequestMapping(SchoolRestControllerConstants.ROOT)
public class SchoolControllerImpl implements SchoolController {

	@Autowired()
	SchoolService schoolService;

	@Override()
	@RequestMapping(value = SchoolRestControllerConstants.GET_SCHOOL, method = RequestMethod.GET)
	public @ResponseBody SchoolResponseObject getSchool(@PathVariable final long id) {
		return schoolService.getSchool(id);
	}

	@Override()
	@RequestMapping(value = SchoolRestControllerConstants.GET_SCHOOL_LIST, method = RequestMethod.GET)
	public @ResponseBody List<SchoolResponseObject> getSchoolList() {
		return schoolService.getSchoolList();
	}

	@Override()
	@RequestMapping(value = SchoolRestControllerConstants.DELETE_SCHOOL, method = RequestMethod.DELETE)
	public @ResponseBody Status delete(@PathVariable final long id) {
		schoolService.delete(id);
		return new Status(AttendanceAppUtils.messageToList(SchoolRestControllerConstants.DELETE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
	}

	@Override()
	@RequestMapping(value = SchoolRestControllerConstants.DELETE_SCHOOL_LIST, method = RequestMethod.DELETE)
	public @ResponseBody Status delete(
			@Valid @RequestBody final DeleteSelectedSchoolRequestObject deleteSelectedSchoolRequestObject) {
		schoolService.delete(deleteSelectedSchoolRequestObject);
		return new Status(
				AttendanceAppUtils.messageToList(SchoolRestControllerConstants.SELECTED_SCHOOL_DELETE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
	}

	@Override()
	@RequestMapping(value = SchoolRestControllerConstants.CREATE_SCHOOL, method = RequestMethod.POST)
	public @ResponseBody Status create(@Valid @RequestBody final SchoolCreateRequestObject schoolRequestObject) {
		schoolService.create(schoolRequestObject);
		return new Status(AttendanceAppUtils.messageToList(SchoolRestControllerConstants.CREATE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
	}

	@Override()
	@RequestMapping(value = SchoolRestControllerConstants.UPDATE_SCHOOL, method = RequestMethod.PUT)
	public @ResponseBody SchoolUpdateRequestObject update(
			@Valid @RequestBody final SchoolUpdateRequestObject schoolUpdateRequestObject) {
		schoolService.update(schoolUpdateRequestObject);
		return schoolUpdateRequestObject;
	}

}
