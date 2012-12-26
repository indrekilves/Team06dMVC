package bg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import bg.domain.Type;
import bg.domain.Unit;

public class GenericService {
	
	
	

	public static EntityManagerFactory getEntityManagerFactory() {
		return Persistence.createEntityManagerFactory("my-hsql-unit");
	}
	
	
	
	
	// Integrity checking 
	
	
	

	public List<String> getValidationErrors(Object obj) {
		List<String> errors = new ArrayList<String>();
	
		String name = getNameFromObject(obj);
		if (name == null || name.equals("")) {
		    errors.add("Name is required.");
		}
		
		errors.addAll(getDateValidationErrors(obj));
		
		return errors;
	}





	private String getNameFromObject(Object obj) {
		if (obj.getClass().equals("Type"))
		{
			Type type = (Type) obj;
			return type.getName();
		}
		else if (obj.getClass().equals("Unit"))
		{
			Unit unit = (Unit) obj;
			return unit.getName();
		}

		return null;
	}




	private List<String> getDateValidationErrors(Object obj) {
		List<String> errors = new ArrayList<String>();
	
		Date fromDate 	= getFromDateFromObject(obj);
		Date toDate 	= getToDateFromObject(obj);
		
		if (fromDate == null) {
		    errors.add("From date is required.");
		    return errors;
		}
	
		if (toDate == null) {
		    errors.add("To Date is required.");
		    return errors;
		}
	
	
//		Date fromDate = getDateFromString(fromDate);
//		if (fromDate == null) {
//			errors.add("From date must with format dd.mm.yyyy");
//			return errors;
//		}	
//		
//		Date toDate = getDateFromString(obj.getParameter("toDate"));
//		if (toDate == null) {
//			errors.add("To date must with format dd.mm.yyyy");
//			return errors;
//		}	
//		
//		
//		if (fromDate.after(toDate)){
//			errors.add("From date must be before To date");
//		}		
		
		return errors;
	}





	private Date getFromDateFromObject(Object obj) {
		if (obj.getClass().equals("Type"))
		{
			Type type = (Type) obj;
			return type.getFromDate();
		}
		else if (obj.getClass().equals("Unit"))
		{
			Unit unit = (Unit) obj;
			return unit.getFromDate();
		}

		return null;
	}
		
	
	



	private Date getToDateFromObject(Object obj) {
		if (obj.getClass().equals("Type"))
		{
			Type type = (Type) obj;
			return type.getToDate();
		}
		else if (obj.getClass().equals("Unit"))
		{
			Unit unit = (Unit) obj;
			return unit.getToDate();
		}

		return null;
	}


}
