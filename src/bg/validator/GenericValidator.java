package bg.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import bg.domain.Type;
import bg.domain.Unit;

public class GenericValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", 		"required.name", 		"Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", 		"required.code", 		"Code is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fromDate", 	"required.fromDate", 	"From Date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toDate", 	"required.toDate", 		"To Date is required.");
		
		if (errors.hasErrors()) return;
		
		validateDates(obj, errors);
	}
	
	
	private void validateDates(Object obj, Errors errors) {
		Date fromDate = getFromDate(obj);
		Date toDate   = getToDate(obj);

		if (fromDate == null){
			errors.rejectValue("fromDate", "fromDate.badFormat", "From date must with format dd.mm.yyyy");	
			return;
		}
			
		if (toDate == null){
			errors.rejectValue("toDate", "toDate.badFormat", "To date must with format dd.mm.yyyy");	
			return;
		}
		
		
		if (fromDate.after(toDate)){
			errors.rejectValue("fromDate", "fromDate.afterToDate","From date must be before To date");	
		}		
	}

	
	private Date getFromDate(Object obj) {
		String className = getClassName(obj);
		if (className.equals("bg.domain.Type"))
		{
			Type type = (Type) obj;
			return type.getFromDate();
		}
		else if (className.equals("bg.domain.Unit"))
		{
			Unit unit = (Unit) obj;
			return unit.getFromDate();
		}

		return null;
	}
	
	

	private Date getToDate(Object obj) {
		String className = getClassName(obj);
		if (className.equals("bg.domain.Type"))
		{
			Type type = (Type) obj;
			return type.getToDate();
		}
		else if (className.equals("bg.domain.Unit"))
		{
			Unit unit = (Unit) obj;
			return unit.getToDate();
		}

		return null;
	}

	

	private String getClassName(Object obj) {
		return obj.getClass().getName().toString();
	}
}
