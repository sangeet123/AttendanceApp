package attendanceapp.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.customstatus.Status;
import attendanceapp.util.AttendanceAppUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice()
public class HttpMessageNotReadableExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(HttpMessageNotReadableExceptionHandler.class);

	@ResponseBody()
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Status processSchoolNotFoundException(Exception ex) {
		logger.error("", ex);
		Status status = AttendanceAppUtils.createStatus("", StatusCodeUtil.BAD_REQUEST_ERROR_CODE);
		return status;
	}

}
