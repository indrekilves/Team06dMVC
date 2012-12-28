package bg.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bg.dao.TypeDao;
import bg.dao.UnitDao;
import bg.domain.Type;
import bg.domain.Unit;


@Controller
public class UnitReportController {

	
	
	
	// Properties
	

	
	
	@Resource
	private UnitDao unitDao;
	

	@Resource
	private TypeDao typeDao;
	

	
	
	// Init binder
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	
	
	// Show Unit Report
	
	
	
	
	@RequestMapping(value = "/showUnitReport")
	public String showUnitReport(ModelMap model){
	  			
		System.out.println("Show UnitReport");
	  	
	  	model.addAttribute("date", new Date());
	  	model.addAttribute("types", typeDao.getAllTypes());
	  	
	  	return "unitReport";
	}
	
	
	

	// Refresh
	
	
	

	public String returnToReport(HttpSession session, ModelMap model) {
        Integer typeId 	= (Integer) session.getAttribute("typeId");
        Date date 		= (Date) 	session.getAttribute("date");
		return refreshReport(typeId, date, session, model);
	}
	
	
	
	
	@RequestMapping(value  = "/unitReportAction", 
					params = {"mode=refreshReport", "typeId"},
					method = RequestMethod.POST)
	public String refreshReport(@RequestParam("typeId") Integer typeId, 
								@RequestParam("date") Date date,
								HttpSession session,
								ModelMap model){

		System.out.println("Show UnitReport for TypeID: " + typeId + " Date: " + date);

		List<Unit>	units = unitDao.getAllUnitsWithSuboridinatesByTypeIdAndDate(typeId, date);
		List<Type> 	types = typeDao.getAllTypes();

	  	model.addAttribute("date", date);
	  	model.addAttribute("typeId", typeId);
	  	model.addAttribute("units", units);
	  	model.addAttribute("types", types);
	  		  	
	  	session.setAttribute("typeId", typeId);
	  	session.setAttribute("date", date);
	  	
	  	return "unitReport";
	}



	
	@RequestMapping(value  = "/unitReportAction", 
					params = {"mode=showSelectedEntry", "id"},
					method = RequestMethod.POST)
	public String showUnitReadOnlyForm(@RequestParam("id") Integer id, ModelMap model){
	
		System.out.println("Show Unit Form in readOnly for Unit (ID): " + id );

		Unit unit = unitDao.getUnitWithAssociationById(id);
	  	List <Unit> bossUnits = unitDao.getAllPossibleBossUnitsByUnit(unit); 

	  	model.addAttribute("unit", unit);
	  	model.addAttribute("bossUnits", bossUnits);
	  	model.addAttribute("origin", "unitReport");
	  	
	  	return "unitForm";
	}




	

	
}
