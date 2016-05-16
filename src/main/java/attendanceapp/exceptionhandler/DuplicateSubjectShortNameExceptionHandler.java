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
import attendanceapp.exceptions.DuplicateSubjectShortNameException;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class DuplicateSubjectShortNameExceptionHandler {

	private MessageSource messageSource;
	private final Logger logger = LoggerFactory.getLogger(DuplicateSubjectShortNameExceptionHandler.class);

	@Autowired()
	public DuplicateSubjectShortNameExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody()
	@ExceptionHandler(DuplicateSubjectShortNameException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Status processDuplicateSchoolNameException(DuplicateSubjectShortNameException ex) {
		logger.error("Duplicate subject short name", ex);
		return AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.DUPLICATE_ITEM_EXIST_CODE);
	}
}
