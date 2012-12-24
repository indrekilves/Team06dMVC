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
	  	
	  	List <Type> types = typeDao.findAll();
	  	
	  	System.out.println("Show list of types: " + types);
	  	
	  	model.addAttribute("types", types);
	  	
	  	return "typesList";
	}
	
	
	// Edit type
	
	
	@RequestMapping(value = "/typesListAction", params = "exitMode=showSelectedEntry")
	public String showTypeForm(HttpServletRequest request, @RequestParam("id") Integer id,  ModelMap model){
	  	
		String action 		= "default";
		String origin 		= request.getParameter("origin");
		String exitMode		= request.getParameter("exitMode");		
		String id2	 		= request.getParameter("id");
		String subId 		= request.getParameter("subId");
	  	
		List <Type> types = typeDao.findAll();
	  	
	  	System.out.println("Show list of types: " + types);
	  	
	  	model.addAttribute("types", types);
	  	
	  	return "typesList";
	}
	
}
