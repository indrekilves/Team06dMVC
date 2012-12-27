package bg.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import bg.dao.UnitDao;
import bg.domain.Unit;


@Component
public class UnitValidator extends GenericValidator {

	
	@Resource
	private UnitDao unitDao;




	@Override
	public boolean supports(Class<?> clazz) {
		return Unit.class.isAssignableFrom(clazz);
	}

	
	
	@Override
	public void validate(Object target, Errors errors) {
		super.validate(target, errors);

		if (errors.hasErrors()) return;

		Unit unit = (Unit) target;
		validateCode(unit, errors);
	}
	
	
	

	private void validateCode(Unit unit, Errors errors) {
		// Check if code is used by some OTHER type
		if (unit.getId() != null)
		{
			// Code on existing record is changed
			Unit oldType = unitDao.getUnitById(unit.getId());
			if (oldType != null) 
			{
				if (oldType.getCode().equals(unit.getCode()) == false) 
				{
					if (unitDao.isCodeExisting(unit.getCode())){
						errors.rejectValue("code", "code.inUse", "Code '" + unit.getCode() + "' is in use already.");	

					}
				}
			}
		} else {
			if (unitDao.isCodeExisting(unit.getCode())){
				errors.rejectValue("code", "code.inUse", "Code '" + unit.getCode() + "' is in use already.");	
			}
		}			
		
	}

	
	
}
