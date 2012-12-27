package bg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bg.dao.UnitDao;
import bg.domain.Unit;
import bg.service.UnitService;
import bg.validator.UnitValidator;

@Controller
public class UnitController extends GenericController {

	
	
	
	// Properties
	
	
	
	
	@Resource
	private UnitDao unitDao;

	@Resource
	private UnitService unitService;

	@Resource
	private UnitValidator unitValidator;


	
	
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
	
	
	
	
	// Save type
	
	

	
	@RequestMapping(value = "/unitFormAction", params = "mode=saveForm", method = RequestMethod.POST)
    public String saveUnitForm(@ModelAttribute("unit") Unit unit, BindingResult bindingResult, @RequestParam("bossId") Integer bossId, ModelMap model) {

		unitValidator.validate(unit, bindingResult);
		if (bindingResult.hasErrors()) {
			return showTypeFormWithErrors(unit, bossId, model);
		}

        unit = unitDao.save(unit); 
        
	  	System.out.println("Save unit: " + unit);

        unitService.updateBossByIds(unit.getId(), bossId);

        return "redirect:showUnitsList";
    }




	private String showTypeFormWithErrors(Unit unit, Integer bossId, ModelMap model) {
		// Reload type
		unit = unitService.reloadUnitByBossId(unit, bossId); 
				
		// Reload bossTypes
		List <Unit> bossUnits = unitDao.getAllPossibleBossUnitsByUnit(unit);
		
		System.out.println("Save failed - show form with errors for unit: " + unit);
		
		model.addAttribute("unit", unit);
		model.addAttribute("bossUnits", bossUnits);
		
		return "unitForm";		
	}

	
	
	
	
	// Cancel type 
	
	
	
	@RequestMapping(value = "/unitFormAction", params = "mode=cancelForm")
    public String cancelForm() {
        return "redirect:showUnitsList";
    }	
	
}
