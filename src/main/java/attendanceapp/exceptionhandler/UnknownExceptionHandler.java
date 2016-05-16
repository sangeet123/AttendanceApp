package attendanceapp.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.exceptions.UnknownException;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class UnknownExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(UnknownExceptionHandler.class);

	@ResponseBody()
	@ExceptionHandler(UnknownException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Status processUnknownException(UnknownException ex) {
		logger.error("", ex);
		return AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.INTERNAL_ERROR_CODE);
	}
}
