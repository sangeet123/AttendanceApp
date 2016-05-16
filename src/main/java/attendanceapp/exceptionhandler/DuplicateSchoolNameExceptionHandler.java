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
import attendanceapp.exceptions.DuplicateSchoolNameException;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class DuplicateSchoolNameExceptionHandler {

	private MessageSource messageSource;
	private final Logger logger = LoggerFactory.getLogger(DuplicateSchoolNameExceptionHandler.class);

	@Autowired()
	public DuplicateSchoolNameExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody()
	@ExceptionHandler(DuplicateSchoolNameException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Status processDuplicateSchoolNameException(DuplicateSchoolNameException ex) {
		logger.error("Duplicate school name", ex);
		Status status = AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.DUPLICATE_ITEM_EXIST_CODE);
		return status;
	}
}
