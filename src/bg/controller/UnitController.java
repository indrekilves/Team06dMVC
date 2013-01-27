package bg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import bg.dao.TypeDao;
import bg.dao.UnitDao;
import bg.domain.Type;
import bg.domain.Unit;
import bg.service.UnitService;
import bg.validator.UnitValidator;

@Controller
public class UnitController {

	
	
	
	// Properties
	
	
	
	
	@Resource
	private UnitDao unitDao;
	
	@Resource
	private UnitService unitService;

	@Resource
	private UnitValidator unitValidator;

	@Resource
	private TypeDao typeDao;
	
	@Resource
	private UnitReportController unitReportController;

	
	
	// Init binder
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	
	
	// Show list
	
	
	
	@RequestMapping(value = "/showUnitList", produces = "text/plain;charset=UTF-8")
	public String showUnitList(ModelMap model){
	  	
	  	List <Unit> units = unitDao.getAllUnits();
	  			
	  	System.out.println("Show list of units: " + units);
	  	
	  	model.addAttribute("units", units);
	  	
	  	return "unitList";
	}
	
	
	
	
	// Remove type
	

	
	
	@RequestMapping(value = "/unitListAction", 
					params = {"mode=removeSelectedEntry", "id"},
					produces = "text/plain;charset=UTF-8")
	public String removeUnit(@RequestParam("id") Integer id,  ModelMap model){
	  	Unit unit = unitDao.getUnitById(id);
	  	
		System.out.println("Remove unit: " + unit);
	  	
		unitDao.closeUnit(unit);
	  	
	  	return "redirect:showUnitList";
	}
	
	
	
	
	// Add type
	
	
	
	
	@RequestMapping(value = "/unitListAction", params = "mode=addEntry")
	public String addUnit(ModelMap model){
	 	System.out.println("Start adding new unit.");
	 	
	  	List <Unit> bossUnits = unitDao.getAllUnits(); 
	  	model.addAttribute("bossUnits", bossUnits);
	  	
	  	return "unitForm";
	}
	
	
	
	
	// Edit unit
	
	
	
	
	@RequestMapping(value = "/unitListAction", 
					params = {"mode=showSelectedEntry", "id"},
					produces = "text/plain;charset=UTF-8")
	public String showUnitForm(@RequestParam("id") Integer id,  ModelMap model, HttpSession session){
		Unit unit = unitDao.getUnitWithAssociationById(id);
	  	List <Unit> bossUnits = unitDao.getAllPossibleBossUnitsByUnit(unit); 

	  	System.out.println("Show form for unit: " + unit);
	  	
	  	model.addAttribute("unit", unit);
	  	model.addAttribute("bossUnits", bossUnits);
	  	
	  	session.setAttribute("unitId", id);

	  	return "unitForm";
	}
	
	
	
	
	// Save type
	
	

	
	@RequestMapping(value    = "/unitFormAction", 
					params   = "mode=saveForm", 
					method   = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
    public String saveUnitForm(	@Valid @ModelAttribute("unit") Unit unit, 
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

        return "redirect:showUnitList";
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
	
	
	
	
	@RequestMapping(value = "/unitFormAction", params = "mode=cancelForm", method = RequestMethod.POST)
    public String cancelForm(@RequestParam("origin") String origin, HttpSession session, ModelMap model) {
		if (origin != null && origin.equals("unitReport"))
		{
			return unitReportController.returnToReport(session, model);
		}
		else
		{
			return "redirect:showUnitList";
		}
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
    								ModelMap model,
    								HttpSession session) {
		// first save the type
		String nextPage = saveUnitForm(unit, bindingResult, bossId, model);
		if (bindingResult.hasErrors()){
			return nextPage;
		}
		
	  	System.out.println("Remove subOrdinate (ID): " + subId + " from unit: " + unit);

		// then remove the subOrdinate
		unitService.removeSubOrdinateByIds(unit.getId(), subId);
        return showUnitForm(unit.getId(), model, session);
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
		// first save the unit
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
	
	
	@RequestMapping(value = "/addUnitSubordinateAction", 
					params = {"mode=selectSubOrdinate", "id", "subId"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	private String saveSubOrdinate(	@RequestParam("id") Integer id, 
									@RequestParam("subId") Integer subId, 
									ModelMap model,
									HttpSession session) {
		unitService.addSubOrdinateByIds(id, subId);
	  	System.out.println("Add subOrdinate (ID): " + subId + " to Unit (ID): " + id);
		return showUnitForm(id, model, session);
	}


	@RequestMapping(value = "/addUnitSubordinateAction", 
					params = {"mode=cancelSubordinateSelect", "id"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	private String cancelSubordinateSelect(@RequestParam("id") Integer id, ModelMap model, HttpSession session) {
		return showUnitForm(id, model, session);
	}
	
	
	
	
	// Change type
	
	
	
	@RequestMapping(value = "/unitFormAction", 
					params = "mode=changeType", 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	public String changeType(@ModelAttribute("unit") Unit unit, 
						BindingResult bindingResult, 
						@RequestParam("bossId") Integer bossId,
						ModelMap model) {

		// first save the unit
		String nextPage = saveUnitForm(unit, bindingResult, bossId, model);
		if (bindingResult.hasErrors()){
			return nextPage;
		}

		// then change the type		

		System.out.println("Change Type for unit: " + unit);
		
		List<Type> types = typeDao.getAllTypes();

		model.addAttribute("unit", unit);
		model.addAttribute("types", types);
				
		return "unitTypesList";
	}
	
	
	
	
	@RequestMapping(value = "/changeTypeListAction", 
					params = {"mode=selectType", "id", "typeId"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	public String selectType(	@RequestParam("id") Integer id, 
								@RequestParam("typeId") Integer typeId,
								ModelMap model,
								HttpSession session) {
		
		unitService.changeTypeByIds(id, typeId); 
		
		System.out.println("Save Type for unit (ID): " + id + ". New type (ID): " + typeId);
			
		return showUnitForm(id, model, session);
	}
	
	
	
	@RequestMapping(value = "/changeTypeListAction", 
					params = {"mode=cancelTypeSelect", "id"}, 
					method = RequestMethod.POST,
					produces = "text/plain;charset=UTF-8")
	public String cancelTypeSelect(@RequestParam("id") Integer id, ModelMap model, HttpSession session) {
		return showUnitForm(id, model, session);
	}
	
	
	
	
	// Language change
	
	
	
	@RequestMapping(value = "/unitListAction", params="lang")
	public String languageChangeFromUnitForm(HttpSession session, ModelMap model){
		Integer id = (Integer) session.getAttribute("unitId");
		if (id != null && id > 0) {
			return showUnitForm(id, model, session);
		}
		else{
			return showUnitList(model);
		}
	}
	
	
	
	@RequestMapping(value = "/unitFormAction", params="lang")
	public String languageChangeFromUnitFromLists(HttpSession session, ModelMap model){
			return languageChangeFromUnitForm(session, model);
	}

	
}
