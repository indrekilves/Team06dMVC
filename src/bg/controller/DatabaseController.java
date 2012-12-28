package bg.controller;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import bg.dao.TypeAssociationDao;
import bg.dao.TypeDao;
import bg.dao.UnitAssociationDao;
import bg.dao.UnitDao;
import bg.domain.Type;
import bg.domain.TypeAssociation;
import bg.domain.Unit;
import bg.domain.UnitAssociation;
import bg.service.DatabaseService;


@Controller
public class DatabaseController {
	
	
	// Properties 
	
	
	@Resource
	private DatabaseService databaseService;
	
	@Resource
	private TypeDao typeDao;
	
	@Resource
	private TypeAssociationDao typeAssociationDao;

	@Resource
	private UnitDao unitDao;

	@Resource
	private UnitAssociationDao unitAssociationDao;
	
	
	// Insert test data
	
	
	@RequestMapping(value = "/insertTestData")
	public String insertTestData(ModelMap model){
	  	
		databaseService.insertTestData();	
	  	
	  	List <Type> types = typeDao.getAllTypes();
	  	List <TypeAssociation> typeAssociations = typeAssociationDao.getAllTypeAccociations();

		List <Unit> units = unitDao.getAllUnits();
	  	List <UnitAssociation> unitAssociations = unitAssociationDao.getAllUnitAccociations();

	  	
	  	System.out.println("Inserted types: " + types);
	  	System.out.println("Inserted type associations: " + typeAssociations);
	  	System.out.println("Inserted units: " + units);
	  	System.out.println("Inserted unit associations: " + unitAssociations);

		model.addAttribute("status", "Test data is inserted.");
	  	model.addAttribute("types", types);
	  	model.addAttribute("typeAssociations", typeAssociations);
	  	model.addAttribute("units", units);
	  	model.addAttribute("unitAssociations", unitAssociations);
	  	
	  	return "database";
	}
	
	
	// Delete all existing data
	
	
	@RequestMapping(value = "/deleteAllData")
	public String deleteAllData(ModelMap model){
	  	
		databaseService.deleteExistingData();	
	  	
		model.addAttribute("status", "All existing data is deleted.");
	  	return "database";
	}
	
	
	// Clear lock / delete database 
	
	
	@RequestMapping(value = "/clearDbLock")
	public String clearDbLock(ModelMap model){
	  	
		String status = databaseService.clearDbLock();	
	  	
		model.addAttribute("status", status);
	  	return "database";
	}
	
		
	
	
	@RequestMapping(value = "/deleteDatabase")
	public String deleteDatabase(ModelMap model){
	  	
		String status = databaseService.deleteDatabase();	
	  	
		model.addAttribute("status", status);
	  	return "database";
	}
	
}
