package attendanceapp.dao.validator;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;

import attendanceapp.modelvalidation.ValidationError;

public class ValidationErrorProcessor {

	private MessageSource messageSource;

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ValidationError processFieldErrors(final List<FieldError> fieldErrors) {
		final ValidationError validationError = new ValidationError();

		for (FieldError fieldError : fieldErrors) {
			String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
			validationError.addFieldError(fieldError.getField(), localizedErrorMessage);
		}

		return validationError;
	}

	public String resolveLocalizedErrorMessage(final FieldError fieldError) {
		final Locale currentLocale = LocaleContextHolder.getLocale();
		final String[] fieldErrorCodes = fieldError.getCodes();
		String localizedErrorMessage = messageSource.getMessage(fieldErrorCodes[0], null, currentLocale);

		// if message is not found, return the field error code instead
		if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
			localizedErrorMessage = fieldErrorCodes[0];
		}

		return localizedErrorMessage;
	}

	public ValidationError processDaoFieldErrors(Set<String> fields) {
		final ValidationError validationError = new ValidationError();
		final Locale currentLocale = LocaleContextHolder.getLocale();
		fields.forEach(
				field -> validationError.addFieldError(field, messageSource.getMessage(field, null, currentLocale)));
		return validationError;
	}

}
