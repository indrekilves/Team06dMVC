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

@Controller
public class TypeController extends GenericController{

	
	// Properties
	
	
	@Resource
	private TypeDao typeDao;
	
	private TypeService typeService;

	
//	@Autowired
//    private Validator validator;
	
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
    public String saveForm(@ModelAttribute("type") Type type, BindingResult bindingResult, ModelMap model) {

//        List<String> errors = typeService.getValidationErrors(type); 
//        if (!errors.isEmpty()) {
//            model.addAttribute("errors", errors);
//            showTypeForm(type.getId(), model);
//        }

        typeDao.save(type);

        return "redirect:showTypesList";
    }
	
	
	
	
	// Cancel type 
	
	
	
	@RequestMapping(value = "/typeFormAction", params = "mode=cancelForm")
    public String cancelForm() {
        return "redirect:showTypesList";
    }	
	
	
	
	
	
	
	
	
	
}
