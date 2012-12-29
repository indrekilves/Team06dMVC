package bg.controller;

import java.util.List;

import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import bg.domain.Type;
import bg.service.TypeTreeService;

@Controller
public class TypeTreeController {

	
	@Resource
	private TypeTreeService typeTreeService;
	
	
	
	@RequestMapping(value = "/showTypeTree")
	public String showTypeTree(ModelMap model){
	  			
		System.out.println("Show TypeTree");
	  	
		return "typeTree";
	}


	
	
	@RequestMapping(value="/showTypeTree", params="root", produces="application/json;charset=UTF-8")
    public @ResponseBody String showTypeTreeForRoot(@RequestParam("root") String root) throws JSONException {
		JSONArray jsonItems = new JSONArray();
		
		List <Type> types = typeTreeService.getTypesByRoot(root);
		if (types != null && !types.isEmpty()){
			for (Type type : types) {
				jsonItems.put(getJsonItemFromType(type));
			}
		}

		System.out.println("Show TypeTree for root: " + root + ". Found Types: " + jsonItems.toString());
	    return jsonItems.toString();
    }




	private JSONObject getJsonItemFromType(Type type) throws JSONException {
	    JSONObject jsonItem = new JSONObject();
	    
	    jsonItem.put("id", type.getId());
	    jsonItem.put("text", type.getName());
	    jsonItem.put("expanded", true);
	    jsonItem.put("children", getChildenFromType(type));
	    jsonItem.put("hasChildren", hasChildren(type));
	    
		return jsonItem;
	}


	private JSONArray getChildenFromType(Type type) throws JSONException {
		JSONArray children = new JSONArray();
		
		List <Type> subOrdinates = type.getSubOrdinates();
		if (subOrdinates != null && !subOrdinates.isEmpty())
		{
			for (Type subOrdinate : subOrdinates) 
			{
				if (subOrdinate != null)
				{
					JSONObject child = getJsonItemFromType(subOrdinate);
					children.put(child);
				}
			}
			
		}
		
		return children;
	}
	

	
	private boolean hasChildren(Type type) {
		List <Type> subOrdinates = type.getSubOrdinates();
		return subOrdinates != null && !subOrdinates.isEmpty();
	}

	
	
}
