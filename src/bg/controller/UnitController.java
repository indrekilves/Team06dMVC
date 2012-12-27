package bg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bg.dao.UnitDao;
import bg.domain.Unit;

@Controller
public class UnitController extends GenericController {

	
	
	
	// Properties
	
	
	
	
	@Resource
	private UnitDao unitDao;

	
	
	
	@RequestMapping(value = "/showUnitsList")
	public String showUnitsList(ModelMap model){
	  	
	  	List <Unit> units = unitDao.getAllUnits();
	  			
	  	System.out.println("Show list of units: " + units);
	  	
	  	model.addAttribute("units", units);
	  	
	  	return "unitsList";
	}
	
	
}
