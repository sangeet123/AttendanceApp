package attendanceapp.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.dao.validator.ValidationErrorProcessor;
import attendanceapp.exceptions.ConflictException;
import attendanceapp.modelvalidation.ValidationError;

@ControllerAdvice()
public class ConflictExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(ConflictExceptionHandler.class);

	@Autowired()
	private ValidationErrorProcessor errorProcessor;

	@ResponseBody()
	@ExceptionHandler(ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ValidationError processDuplicateSchoolNameException(ConflictException ex) {
		logger.error("", ex);
		return errorProcessor.processDaoFieldErrors(ex.getFields());
	}
}
