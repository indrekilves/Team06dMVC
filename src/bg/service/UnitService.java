package bg.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import bg.dao.UnitAssociationDao;
import bg.dao.UnitDao;
import bg.domain.Unit;

@Service
public class UnitService extends GenericService{

	
	// Properties
	
	
	@Resource
	private UnitDao unitDao;
	
	@Resource
	private UnitAssociationDao unitAssociationDao;


	
	
	// Reload type
	
	
	

	public Unit reloadUnitByBossId(Unit unit, Integer bossId) {
		// Reload boss
		Unit boss = unitDao.getUnitById(bossId);
		unit.setBoss(boss);

		// Reload subordinates
		List <Unit> subOrdinates = unitDao.getSubOrdinatesById(unit.getId());
		unit.setSubOrdinates(subOrdinates);		
		
		return unit;
	}


	
	// Update boss


	public void updateBossByIds(Integer id, Integer newBossId) {
		if (id == null) return;
		
		Unit unit = unitDao.getUnitWithAssociationById(id);
		Unit oldBoss = unit.getBoss();

		Integer oldBossId = oldBoss != null ? oldBoss.getId() : null;
	
		if (oldBossId == newBossId) return;
		
		unitAssociationDao.replaceBossAssociation(oldBossId, newBossId, id);	
	}



}
