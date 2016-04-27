package attendanceapp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import attendanceapp.constants.SchoolRestControllerConstants;
import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.SchoolRequestObject;
import attendanceapp.model.responseobject.SchoolResponseObject;

public interface SchoolController {

	@RequestMapping(value = SchoolRestControllerConstants.GET_SCHOOL, method = RequestMethod.GET)
	public @ResponseBody SchoolResponseObject getSchool(
			@PathVariable final long id);

	@RequestMapping(value = SchoolRestControllerConstants.GET_SCHOOL_LIST, method = RequestMethod.GET)
	public @ResponseBody List<SchoolResponseObject> getSchoolList();

	@RequestMapping(value = SchoolRestControllerConstants.DELETE_SCHOOL, method = RequestMethod.DELETE)
	public @ResponseBody Status delete(@PathVariable final long id);

	@RequestMapping(value = SchoolRestControllerConstants.DELETE_SCHOOL_LIST, method = RequestMethod.DELETE)
	public @ResponseBody Status delete(@RequestBody final String ids);

	@RequestMapping(value = SchoolRestControllerConstants.CREATE_SCHOOL, method = RequestMethod.POST)
	public @ResponseBody Status create(
			@Valid @RequestBody final SchoolRequestObject schoolRequestObject);

	@RequestMapping(value = SchoolRestControllerConstants.UPDATE_SCHOOL, method = RequestMethod.PUT)
	public @ResponseBody Status update(
			@Valid @RequestBody final SchoolRequestObject schoolRequestObject);
}
