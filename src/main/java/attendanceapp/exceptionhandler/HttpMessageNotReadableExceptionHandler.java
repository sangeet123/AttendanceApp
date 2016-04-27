package attendanceapp.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import attendanceapp.customstatus.Status;
import attendanceapp.util.SchoolRestServiceUtils;
import attendanceapp.util.StatusCodeUtil;

@ControllerAdvice
public class HttpMessageNotReadableExceptionHandler {

	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Status processSchoolNotFoundException(Exception ex) {
		Status status = SchoolRestServiceUtils.createStatus("",
				StatusCodeUtil.BAD_REQUEST_ERROR_CODE);
		return status;
	}

}
