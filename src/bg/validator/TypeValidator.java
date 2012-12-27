package bg.validator;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import bg.dao.TypeDao;
import bg.domain.Type;


@Component
public class TypeValidator extends GenericValidator {

	
	@Resource
	private TypeDao typeDao;




	@Override
	public boolean supports(Class<?> clazz) {
		return Type.class.isAssignableFrom(clazz);
	}

	
	
	@Override
	public void validate(Object target, Errors errors) {
		super.validate(target, errors);

		if (errors.hasErrors()) return;

		Type type = (Type) target;
		validateCode(type, errors);
	}
	
	
	

	private void validateCode(Type type, Errors errors) {
		// Check if code is used by some OTHER type
		if (type.getId() != null)
		{
			// Code on existing record is changed
			Type oldType = typeDao.getTypeById(type.getId());
			if (oldType != null) 
			{
				if (oldType.getCode().equals(type.getCode()) == false) 
				{
					if (typeDao.isCodeExisting(type.getCode())){
						errors.rejectValue("code", "code.inUse", "Code '" + type.getCode() + "' is in use already.");	

					}
				}
			}
		} else {
			if (typeDao.isCodeExisting(type.getCode())){
				errors.rejectValue("code", "code.inUse", "Code '" + type.getCode() + "' is in use already.");	
			}
		}			
		
	}

	
}