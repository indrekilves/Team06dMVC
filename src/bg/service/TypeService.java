package bg.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bg.dao.TypeDao;
import bg.domain.Type;


@Service
public class TypeService extends GenericService {

	
	// Properties
	
	
	@Resource
	private TypeDao typeDao;


	
	
	// Integrity checking 
	
	
	

	public List<String> getValidationErrors(Type type) {
		List<String> errors = super.getValidationErrors(type);
		errors.addAll(getCodeValidationErrors(type));
		
		return errors;
	}


	private List<String> getCodeValidationErrors(Type type) {
		List<String> errors = new ArrayList<String>();

		if (type.getCode() == null || type.getCode().length() < 1) {
		    errors.add("Code is required.");
		    return errors;
		}		

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
						errors.add("Code '" + type.getCode() + "' is in use already.");
					}
				}
			}
		} else {
			if (typeDao.isCodeExisting(type.getCode())){
				errors.add("Code '" + type.getCode() + "' is in use already.");
			}
		}			
		
		return errors;
	}

	
	
}
