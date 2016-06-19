package attendanceapp.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import attendanceapp.dao.validator.ValidationErrorProcessor;
import attendanceapp.modelvalidation.ValidationError;

@ControllerAdvice()
public class ModelValidationExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(ModelValidationExceptionHandler.class);

	@Autowired()
	private ValidationErrorProcessor errorProcessor;

	@ResponseBody()
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationError processValidationError(MethodArgumentNotValidException ex) {
		logger.error("", ex);
		BindingResult result = ex.getBindingResult();
		return errorProcessor.processFieldErrors(result.getFieldErrors());
	}
}
