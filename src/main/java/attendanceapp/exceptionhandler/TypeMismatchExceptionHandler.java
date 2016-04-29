package attendanceapp.exceptionhandler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.constants.Constant;
import attendanceapp.customstatus.Status;
import attendanceapp.util.SchoolRestServiceUtils;
import attendanceapp.util.StatusCodeUtil;

/*
 * All the ids in this application are long.
 * Passing id that are not convertible throws this
 * exception. This is the class for handling those 
 * exceptions. Also make sure that service and dao layer
 * does not throw this exception.
 */
@ControllerAdvice()
public class TypeMismatchExceptionHandler {
	@ResponseBody()
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Status processSchoolNotFoundException(Exception ex) {
		Status status = SchoolRestServiceUtils.createStatus(Constant.RESOURSE_DOES_NOT_EXIST,
				StatusCodeUtil.ITEM_NOT_FOUND_CODE);
		return status;
	}
}
