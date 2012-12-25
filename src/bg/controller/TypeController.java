package bg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import bg.dao.TypeDao;
import bg.domain.Type;

@Controller
public class TypeController extends GenericController{

	
	// Properties
	
	
	@Resource
	private TypeDao typeDao;
	
	
	// Show list
	
	
	@RequestMapping(value = "/showTypesList")
	public String showTypesList(ModelMap model){
	  	
	  	List <Type> types = typeDao.getAllTypes();
	  	
	  	System.out.println("Show list of types: " + types);
	  	
	  	model.addAttribute("types", types);
	  	
	  	return "typesList";
	}
	
	
	// Edit type
	
	
	@RequestMapping(value = "/typesListAction", params = {"exitMode=showSelectedEntry", "id"})
	public String showTypeForm(HttpServletRequest request, @RequestParam("id") Integer id,  ModelMap model){
	  	
		Type type = typeDao.getTypeWithAssociationById(id);
	  	List <Type> bossTypes = typeDao.getAllPossibleBossTypesByType(type); 
	  	// TODO - find boss types

	  	System.out.println("Show form for type: " + type);
	  	
	  	model.addAttribute("type", type);
	  	model.addAttribute("bossTypes", bossTypes);
	  	
	  	return "typeForm";
	}
	
}
