package es.web.rest.custom;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ValidationErrorUtils {

  public static ValidationError getValidationErrors(Errors errors) {

    ValidationError errorValidation = new ValidationError();
    List<ValidationFieldError> fieldsErrorValidation = new ArrayList<>();

    ValidationFieldError fieldErrorValidation = null;
    List<FieldError> fieldErrors = errors.getFieldErrors();

    for (FieldError fieldError : fieldErrors) {
      fieldErrorValidation = new ValidationFieldError();
      fieldErrorValidation.setResource(fieldError.getObjectName());
      fieldErrorValidation.setField(fieldError.getField());
      fieldErrorValidation.setCode(fieldError.getCode());
      fieldErrorValidation.setMessage(
          fieldError.getField() + ": " + fieldError.getDefaultMessage());
      fieldsErrorValidation.add(fieldErrorValidation);
    }

    errorValidation.setEntity(errors.getClass().getName());
    errorValidation.setFieldErrors(fieldsErrorValidation);

    return errorValidation;
  }
}
