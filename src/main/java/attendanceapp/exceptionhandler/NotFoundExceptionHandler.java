package attendanceapp.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.exceptions.NotFoundException;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class NotFoundExceptionHandler {

	private MessageSource messageSource;
	private final Logger logger = LoggerFactory.getLogger(NotFoundExceptionHandler.class);

	@Autowired()
	public NotFoundExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody()
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Status processSchoolNotFoundException(NotFoundException ex) {
		logger.error("School not found", ex);
		Status status = AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.ITEM_NOT_FOUND_CODE);
		return status;
	}
}
