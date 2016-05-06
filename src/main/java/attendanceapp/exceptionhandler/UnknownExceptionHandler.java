package attendanceapp.exceptionhandler;

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
	@ResponseBody()
	@ExceptionHandler(UnknownException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Status processUnknownException(UnknownException ex) {
		Status status = AttendanceAppUtils.createStatus(ex.getMessage(), StatusCodeUtil.INTERNAL_ERROR_CODE);
		return status;
	}
}
