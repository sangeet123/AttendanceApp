package attendanceapp.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import attendanceapp.modelvalidation.ValidationError;

@ControllerAdvice
public class ModelValidationExceptionHandler {

	private MessageSource messageSource;

	@Autowired
	public ModelValidationExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationError processValidationError(
			MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();

		ValidationError validationError = processFieldErrors(fieldErrors);
		return validationError;
	}

	private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
		ValidationError validationError = new ValidationError();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			validationError.addFieldError(fieldError.getField(),
					localizedErrorMessage);
		}

		return validationError;
	}

	private String resolveLocalizedErrorMessage(FieldError fieldError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String[] fieldErrorCodes = fieldError.getCodes();
		String localizedErrorMessage = messageSource.getMessage(
				fieldErrorCodes[0], null, currentLocale);

		// if message is not found, return the field error code instead
		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
	}
}
