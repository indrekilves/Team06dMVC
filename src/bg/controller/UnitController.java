package bg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	
	@RequestMapping(value = "/showUnitsList", produces = "text/plain;charset=UTF-8")
	public String showUnitsList(ModelMap model){
	  	
	  	List <Unit> units = unitDao.getAllUnits();
	  			
	  	System.out.println("Show list of units: " + units);
	  	
	  	model.addAttribute("units", units);
	  	
	  	return "unitsList";
	}
	
	
	
	
	// Remove type
	

	
	
	@RequestMapping(value = "/unitListAction", 
					params = {"mode=removeSelectedEntry", "id"},
					produces = "text/plain;charset=UTF-8")
	public String removeType(@RequestParam("id") Integer id,  ModelMap model){
	  	Unit unit = unitDao.getUnitById(id);
	  	
		System.out.println("Remove unit: " + unit);
	  	
		unitDao.closeUnit(unit);
	  	
	  	return "redirect:showUnitsList";
	}
	
	
	
	
	// Edit unit
	
	
	
	
	@RequestMapping(value = "/unitListAction", 
					params = {"mode=showSelectedEntry", "id"},
					produces = "text/plain;charset=UTF-8")
	public String showUnitForm(@RequestParam("id") Integer id,  ModelMap model){
		Unit unit = unitDao.getUnitWithAssociationById(id);
	  	List <Unit> bossUnits = unitDao.getAllPossibleBossUnitsByUnit(unit); 

	  	System.out.println("Show form for unit: " + unit);
	  	
	  	model.addAttribute("unit", unit);
	  	model.addAttribute("bossUnits", bossUnits);
	  	
	  	return "unitForm";
	}
	
	
	
	
	// Save type
	
	

	
	@RequestMapping(value    = "/unitFormAction", 
					params   = "mode=saveForm", 
					method   = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
    public String saveUnitForm(	@ModelAttribute("unit") Unit unit, 
    							BindingResult bindingResult, 
    							@RequestParam("bossId") Integer bossId, 
    							ModelMap model) {

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
	
	
	
	@RequestMapping(value = "/unitFormAction", params = "mode=cancelForm", produces = "text/plain;charset=UTF-8")
    public String cancelForm() {
        return "redirect:showUnitsList";
    }	
	
	

	
	// Remove subOrdinate
	
	
	
	@RequestMapping(value = "/unitFormAction", 
					params = {"mode=removeSubOrdinate", "subId"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
    public String removeSubOrdinate(@ModelAttribute("unit") Unit unit, 
    								BindingResult bindingResult, 
    								@RequestParam("bossId") Integer bossId,
    								@RequestParam("subId") Integer subId, 
    								ModelMap model) {
		// first save the type
		String nextPage = saveUnitForm(unit, bindingResult, bossId, model);
		if (bindingResult.hasErrors()){
			return nextPage;
		}
		
	  	System.out.println("Remove subOrdinate (ID): " + subId + " from unit: " + unit);

		// then remove the subOrdinate
		unitService.removeSubOrdinateByIds(unit.getId(), subId);
        return showUnitForm(unit.getId(), model);
    }
	
	
	
	
	// Add subOrdinate
	

	
	
	@RequestMapping(value = "/unitFormAction", 
					params = "mode=addSubOrdinate", 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
    public String addSubOrdinate(@ModelAttribute("unit") Unit unit, 
    								BindingResult bindingResult, 
    								@RequestParam("bossId") Integer bossId,
    								ModelMap model) {
		// first save the type
		String nextPage = saveUnitForm(unit, bindingResult, bossId, model);
		if (bindingResult.hasErrors()){
			return nextPage;
		}

		// then add the subOrdinate
		
		unit = unitDao.getUnitWithAssociationById(unit.getId());
		model.addAttribute("unit", unit);

		List<Unit> possibibleSubordinates = unitDao.getAllPossibleSubordinatesByUnit(unit);
		model.addAttribute("possibleSubordinates", possibibleSubordinates);

	  	System.out.println("Start adding subOrdinate to unit: " + unit + ". Possibile subOrdinates are: " + possibibleSubordinates);

        return "unitPossibileSubordinatesList";
    }
	
	
	@RequestMapping(value = "/unitFormAction", 
					params = {"mode=selectSubOrdinate", "id", "subId"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	private String saveSubOrdinate(@RequestParam("id") Integer id, @RequestParam("subId") Integer subId, ModelMap model) {
		unitService.addSubOrdinateByIds(id, subId);
	  	System.out.println("Add subOrdinate (ID): " + subId + " to Unit (ID): " + id);
		return showUnitForm(id, model);
	}


	@RequestMapping(value = "/unitFormAction", 
					params = {"mode=cancelSubordinateSelect", "id"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	private String cancelSubordinateSelect(@RequestParam("id") Integer id, ModelMap model) {
		return showUnitForm(id, model);
	}
	
	
	
	
}