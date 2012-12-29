package bg.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bg.dao.TypeDao;
import bg.domain.Type;

@Service
public class TypeTreeService {

	
	
	@Resource
	private TypeDao typeDao;

	
	public List<Type> getTypesByRoot(String root) {
		List <Type> types = new ArrayList<Type>();
		
		if (root != null && root.length() > 0)
		{
			if (root.equals("source"))
			{
				types = getTopLevelTypes();
			}
			else
			{
				Integer id = Integer.parseInt(root);
				types = getSubOrdinatesById(id);				
			}
		}
		
		return types;
	}


	private List<Type> getTopLevelTypes() {
		List <Type> topLevelTypes = typeDao.getTopLevelTypes();
		if (topLevelTypes == null || topLevelTypes.isEmpty()) return null;
		
		for (Type type : topLevelTypes) {
			type = setAllNestedSubOrdinates(type);
		}
		
		return topLevelTypes;
	}

	private Type setAllNestedSubOrdinates(Type type) {
		List <Type> subOrdinates = typeDao.getAllNestedSubOrdinatesById(type.getId());
		if (subOrdinates != null && !subOrdinates.isEmpty()){
			type.setSubOrdinates(subOrdinates);
		}
		
		return type;
	}

	
	


	private List<Type> getSubOrdinatesById(Integer id) {
		if (id == null) return null;
		return typeDao.getAllNestedSubOrdinatesById(id);

	}

	
	
	
}
