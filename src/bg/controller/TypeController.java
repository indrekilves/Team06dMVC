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


import bg.dao.TypeDao;
import bg.domain.Type;
import bg.service.TypeService;
import bg.validator.TypeValidator;

@Controller
public class TypeController extends GenericController{

	
	// Properties
	
	
	@Resource
	private TypeDao typeDao;

	@Resource
	private TypeService typeService;
	
	@Resource
	private TypeValidator typeValidator;
	
	
	
	
	// Init binder
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	
	
	// Show list
	
	
	
	
	@RequestMapping(value = "/showTypesList")
	public String showTypesList(ModelMap model){
	  	
	  	List <Type> types = typeDao.getAllTypes();
	  	
	  	System.out.println("Show list of types: " + types);
	  	
	  	model.addAttribute("types", types);
	  	
	  	return "typesList";
	}
	
	
	
	
	// Remove type
	

	
	@RequestMapping(value = "/typesListAction", params = {"mode=removeSelectedEntry", "id"})
	public String removeType(@RequestParam("id") Integer id,  ModelMap model){
	  	Type type = typeDao.getTypeById(id);
	  	
		System.out.println("Remove type: " + type);
	  	
		typeDao.closeType(type);
	  	
	  	return "redirect:showTypesList";
	}
	
	
	
	
	// Add type
	
	
	
	
	@RequestMapping(value = "/typesListAction", params = "mode=addEntry")
	public String addType(ModelMap model){
	 	System.out.println("Start adding new type.");
	 	
	  	List <Type> bossTypes = typeDao.getAllTypes(); 
	  	model.addAttribute("bossTypes", bossTypes);
	  	
	  	return "typeForm";
	}

	
	
	// Edit type
	
	
	
	
	@RequestMapping(value = "/typesListAction", params = {"mode=showSelectedEntry", "id"})
	public String showTypeForm(@RequestParam("id") Integer id,  ModelMap model){
		Type type = typeDao.getTypeWithAssociationById(id);
	  	List <Type> bossTypes = typeDao.getAllPossibleBossTypesByType(type); 

	  	System.out.println("Show form for type: " + type);
	  	
	  	model.addAttribute("type", type);
	  	model.addAttribute("bossTypes", bossTypes);
	  	
	  	return "typeForm";
	}
	
	
	
	
	// Save type
	
	
	
	
	@RequestMapping(value = "/typeFormAction", params = "mode=saveForm", method = RequestMethod.POST)
    public String saveTypeForm(@ModelAttribute("type") Type type, BindingResult bindingResult, @RequestParam("bossId") Integer bossId, ModelMap model) {

		typeValidator.validate(type, bindingResult);
		if (bindingResult.hasErrors()) {
			return showTypeFormWithErrors(type, bossId, model);
		}

        type = typeDao.save(type);
        
	  	System.out.println("Save type: " + type);

        typeService.updateBossByIds(type.getId(), bossId);

        return "redirect:showTypesList";
    }




	private String showTypeFormWithErrors(Type type, Integer bossId, ModelMap model) {
		type = typeService.reloadTypeByBossId(type, bossId);
		
		
		// Reload bossTypes
		List <Type> bossTypes = typeDao.getAllPossibleBossTypesByType(type); 
		
		System.out.println("Save failed - show form with errors for type: " + type);
		
		model.addAttribute("type", type);
		model.addAttribute("bossTypes", bossTypes);
		
		return "typeForm";
	}
	
	
	
	
	// Cancel type 
	
	
	
	@RequestMapping(value = "/typeFormAction", params = "mode=cancelForm")
    public String cancelForm() {
        return "redirect:showTypesList";
    }	
	
	
	
	
	// Remove subOrdinate
	
	
	
	@RequestMapping(value = "/typeFormAction", params = {"mode=removeSubOrdinate", "subId"}, method = RequestMethod.POST)
    public String removeSubOrdinate(@ModelAttribute("type") Type type, 
    								BindingResult bindingResult, 
    								@RequestParam("bossId") Integer bossId,
    								@RequestParam("subId") Integer subId, 
    								ModelMap model) {
		// first save the type
		String nextPage = saveTypeForm(type, bindingResult, bossId, model);
		if (bindingResult.hasErrors()){
			return nextPage;
		}
		
	  	System.out.println("Remove subOrdinate (ID): " + subId + " from type: " + type);

		// then remove the subOrdinate
		typeService.removeSubOrdinateByIds(type.getId(), subId);
        return showTypeForm(type.getId(), model);
    }

	
	
	
	// Add subOrdinate
	

	
	@RequestMapping(value = "/typeFormAction", params = "mode=addSubOrdinate", method = RequestMethod.POST)
    public String addSubOrdinate(@ModelAttribute("type") Type type, 
    								BindingResult bindingResult, 
    								@RequestParam("bossId") Integer bossId,
    								ModelMap model) {
		// first save the type
		String nextPage = saveTypeForm(type, bindingResult, bossId, model);
		if (bindingResult.hasErrors()){
			return nextPage;
		}

		// then add the subOrdinate
		
		type = typeDao.getTypeWithAssociationById(type.getId());
		model.addAttribute("type", type);

		List<Type> possibibleSubordinates = typeDao.getAllPossibleSubordinatesByType(type);
		model.addAttribute("possibleSubordinates", possibibleSubordinates);

	  	System.out.println("Start adding subOrdinate to type: " + type + ". Possibile subOrdinates are: " + possibibleSubordinates);

        return "typePossibileSubordinatesList";
    }
	
	
	
	@RequestMapping(value = "/typeFormAction", params = {"mode=selectSubOrdinate", "id", "subId"}, method = RequestMethod.POST)
	private String saveSubOrdinate(@RequestParam("id") Integer id, @RequestParam("subId") Integer subId, ModelMap model) {
		typeService.addSubOrdinateByIds(id, subId);
	  	System.out.println("Add subOrdinate (ID): " + subId + " from type (ID): " + id);
		return showTypeForm(id, model);
	}
	
	
	@RequestMapping(value = "/typeFormAction", params = {"mode=cancelSubordinateSelect", "id"}, method = RequestMethod.POST)
	private String cancelSubordinateSelect(@RequestParam("id") Integer id, ModelMap model) {
		return showTypeForm(id, model);
	}

}
