package bg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bg.dao.TypeAssociationDao;
import bg.dao.TypeDao;
import bg.domain.Type;

@Service
public class TypeService extends GenericService{
	

	// Properties
	
	
	@Resource
	private TypeDao typeDao;
	
	@Resource
	private TypeAssociationDao typeAssociationDao;


	
	
	// Reload type
	
	
	

	public Type reloadTypeByBossId(Type type, Integer bossId) {
		// Reload boss
		Type boss = typeDao.getTypeById(bossId);
		type.setBoss(boss);

		// Reload subordinates
		List <Type> subOrdinates = typeDao.getSubOrdinatesById(type.getId());
		type.setSubOrdinates(subOrdinates);		
		
		return type;
	}



	
	// Update boss 
	
	
	
	
	public void updateBossByIds(Integer id, Integer newBossId) {
		if (id == null) return;
		
		Type type = typeDao.getTypeWithAssociationById(id);
		if (type == null) return;
		
		Type oldBoss = type.getBoss();

		Integer oldBossId = oldBoss != null ? oldBoss.getId() : null;
	
		if (oldBossId == newBossId) return;
		
		typeAssociationDao.replaceBossAssociation(oldBossId, newBossId, id);
	}


	
	
	// Remove subOrdinate
	
	
	

	public void removeSubOrdinateByIds(Integer id, Integer subId) {
		if (id == null || subId == null) return;
		
		typeAssociationDao.removeSubOrdinateByIds(id, subId);
	}



	
	// Add subOrdinate
	
	
	
	
	public void addSubOrdinateByIds(Integer id, Integer subId) {
		if (id == null || subId == null) return;
		
		typeAssociationDao.addSubOrdinateByIds(id, subId);
	}








	
	
	
}
