package attendanceapp.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.exceptions.SubjectNotFoundException;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class SubjectNotFoundExceptionHandler {

	private MessageSource messageSource;

	@Autowired()
	public SubjectNotFoundExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody()
	@ExceptionHandler(SubjectNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Status processSchoolNotFoundException(SubjectNotFoundException ex) {
		Status status = AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.ITEM_NOT_FOUND_CODE);
		return status;
	}
}
