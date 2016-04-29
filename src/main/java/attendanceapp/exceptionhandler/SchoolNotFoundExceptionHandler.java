package attendanceapp.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.exceptions.SchoolNotFoundException;
import attendanceapp.util.SchoolRestServiceUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class SchoolNotFoundExceptionHandler {

	private MessageSource messageSource;

	@Autowired()
	public SchoolNotFoundExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody()
	@ExceptionHandler(SchoolNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Status processSchoolNotFoundException(SchoolNotFoundException ex) {
		Status status = SchoolRestServiceUtils.createStatus(ex.getMessage(),
				StatusCodeUtil.ITEM_NOT_FOUND_CODE);
		return status;
	}
}
