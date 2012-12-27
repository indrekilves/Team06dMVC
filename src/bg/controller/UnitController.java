package bg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bg.dao.UnitDao;
import bg.domain.Unit;

@Controller
public class UnitController extends GenericController {

	
	
	
	// Properties
	
	
	
	
	@Resource
	private UnitDao unitDao;

	
	
	
	// Init binder
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	
	
	// Show list
	
	
	
	@RequestMapping(value = "/showUnitsList")
	public String showUnitsList(ModelMap model){
	  	
	  	List <Unit> units = unitDao.getAllUnits();
	  			
	  	System.out.println("Show list of units: " + units);
	  	
	  	model.addAttribute("units", units);
	  	
	  	return "unitsList";
	}
	
	
	
	
	
	// Edit unit
	
	
	
	
	@RequestMapping(value = "/unitListAction", params = {"mode=showSelectedEntry", "id"})
	public String showUnitForm(@RequestParam("id") Integer id,  ModelMap model){
		Unit unit = unitDao.getUnitWithAssociationById(id);
	  	List <Unit> bossUnits = unitDao.getAllPossibleBossUnitsByUnit(unit); 

	  	System.out.println("Show form for unit: " + unit);
	  	
	  	model.addAttribute("unit", unit);
	  	model.addAttribute("bossUnits", bossUnits);
	  	
	  	return "unitForm";
	}
	
}
