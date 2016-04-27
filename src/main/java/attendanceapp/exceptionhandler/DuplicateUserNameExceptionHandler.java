package attendanceapp.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.exceptions.DuplicateUserNameException;
import attendanceapp.util.SchoolRestServiceUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice
public class DuplicateUserNameExceptionHandler {

	private MessageSource messageSource;

	@Autowired
	public DuplicateUserNameExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody
	@ExceptionHandler(DuplicateUserNameException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Status processDuplicateUserNameException(
			DuplicateUserNameException ex) {
		Status status = SchoolRestServiceUtils.createStatus(ex.getMessage(),
				StatusCodeUtil.DUPLICATE_ITEM_EXIST_CODE);
		return status;
	}

}
