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

import attendanceapp.constants.SubjectRestControllerConstants;
import attendanceapp.controller.SubjectController;
import attendanceapp.customstatus.Status;
import attendanceapp.model.requestobject.DeleteSelectedSubjectRequestObject;
import attendanceapp.model.requestobject.SubjectCreateRequestObject;
import attendanceapp.model.requestobject.SubjectUpdateRequestObject;
import attendanceapp.model.responseobject.SubjectResponseObject;
import attendanceapp.services.SubjectService;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@Controller()
@RequestMapping(SubjectRestControllerConstants.ROOT)
public class SubjectControllerImpl implements SubjectController {

	@Autowired()
	SubjectService subjectService;

	@Override()
	@RequestMapping(value = SubjectRestControllerConstants.GET_SUBJECT, method = RequestMethod.GET)
	public @ResponseBody SubjectResponseObject getSubject(@PathVariable final long schoolId,
			@PathVariable final long subjectId) {
		return subjectService.getSubject(schoolId, subjectId);
	}

	@Override()
	@RequestMapping(value = SubjectRestControllerConstants.GET_SUBJECT_LIST, method = RequestMethod.GET)
	public @ResponseBody List<SubjectResponseObject> getSubjectList(@PathVariable final long schoolId) {
		return subjectService.getSubjectList(schoolId);
	}

	@Override()
	@RequestMapping(value = SubjectRestControllerConstants.DELETE_SUBJECT, method = RequestMethod.DELETE)
	public @ResponseBody Status delete(@PathVariable final long schoolId, @PathVariable final long subjectId) {
		subjectService.delete(schoolId, subjectId);
		return new Status(AttendanceAppUtils.messageToList(SubjectRestControllerConstants.DELETE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
	}

	@Override()
	@RequestMapping(value = SubjectRestControllerConstants.DELETE_SUBJECT_LIST, method = RequestMethod.DELETE)
	public @ResponseBody Status delete(@PathVariable final long schoolId,
			@Valid @RequestBody final DeleteSelectedSubjectRequestObject deleteSelectedSubjectRequestObject) {
		subjectService.delete(schoolId, deleteSelectedSubjectRequestObject);
		return new Status(
				AttendanceAppUtils.messageToList(SubjectRestControllerConstants.SUBJECTS_DELETE_SUCCESS),
				StatusCodeUtil.OPERATION_SUCCESS);
	}

	@Override()
	@RequestMapping(value = SubjectRestControllerConstants.CREATE_SUBJECT, method = RequestMethod.POST)
	public @ResponseBody SubjectResponseObject create(@PathVariable final long schoolId,
			@Valid @RequestBody final SubjectCreateRequestObject subjectCreateRequestObject) {
		return subjectService.create(schoolId, subjectCreateRequestObject);
	}

	@Override()
	@RequestMapping(value = SubjectRestControllerConstants.UPDATE_SUBJECT, method = RequestMethod.PUT)
	public @ResponseBody SubjectResponseObject update(@PathVariable final long schoolId,
			@Valid @RequestBody final SubjectUpdateRequestObject subjectUpdateRequestObject) {
		return subjectService.update(schoolId, subjectUpdateRequestObject);
	}

}
