package attendanceapp.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.exceptions.DuplicateSchoolNameException;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class DuplicateSchoolNameExceptionHandler {

	private MessageSource messageSource;

	@Autowired()
	public DuplicateSchoolNameExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody()
	@ExceptionHandler(DuplicateSchoolNameException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Status processDuplicateSchoolNameException(DuplicateSchoolNameException ex) {
		Status status = AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.DUPLICATE_ITEM_EXIST_CODE);
		return status;
	}
}
