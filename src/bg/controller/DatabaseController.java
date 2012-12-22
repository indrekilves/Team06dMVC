package bg.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import bg.dao.TypeDao;
import bg.dao.TypeSubordinateDao;
import bg.domain.Type;
import bg.domain.TypeSubordinate;


//@Controller
//public class DatabaseController  {

@Controller
//public class DatabaseController extends GenericController {
public class DatabaseController {
	
	@Resource
	private TypeDao typeDao;
	
	@Resource
	private TypeSubordinateDao typeSubordinateDao;
	
	
    @RequestMapping(value = "/insertTestData")
	public String insertTestData(ModelMap model){
    	
    	typeDao.insertTestData();
    	
    	List <Type> types = typeDao.findAll();
    	List <TypeSubordinate> typeSubordinates = typeSubordinateDao.findAll();
    	
    	System.out.println(types);
    	System.out.println(typeSubordinates);
    	
    	model.addAttribute("types", types);
    	model.addAttribute("typeSubordinates", typeSubordinates);
    	
    	return "database";
	}
   

}
