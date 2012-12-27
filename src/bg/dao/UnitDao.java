package bg.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import bg.domain.Type;
import bg.domain.Unit;
import bg.domain.UnitAssociation;
import bg.service.GenericService;

@Repository
public class UnitDao {
	
	
	
	
	// Properties
	
	
	
	@PersistenceContext
    private EntityManager em;

	@Resource
	private UnitAssociationDao unitAssociationDao;

	@Resource
	private TypeDao typeDao;
	
	
	

	// Constructor
	
	
	
	
	public UnitDao(){}


	
	
	// Find all 
	
	

	public List<Unit> getAllUnits() {
	     TypedQuery<Unit> query = em.createQuery("FROM Unit WHERE opened <= NOW() AND closed >= NOW()", Unit.class);
	     List <Unit> units = query.getResultList();
	     
	     if (!units.isEmpty()){
	    	 for (Unit unit : units) {
				unit = setType(unit);
			}
	     }
	     
	     return units;
	}
	

	private Unit setType(Unit unit) {
		if (unit == null || unit.getTypeId() == null) return null;
		
		Type type = typeDao.getTypeById(unit.getTypeId());
		if (type == null) return null;
		
		unit.setType(type);
		return unit;
	}


	
	
	// Add boss
	
	


	public void addBossToUnit(Unit boss, Unit unit) {
		if (boss == null || unit == null) return; 
		
		EntityManagerFactory 	emf = GenericService.getEntityManagerFactory();
		EntityManager 			em 	= emf.createEntityManager();
		em.getTransaction().begin();
		
		List<UnitAssociation> unitAssociations = new ArrayList<UnitAssociation>();		
		UnitAssociation unitAssociation = new UnitAssociation();
		unitAssociation.setBoss(boss);
		unitAssociation.setSubOrdinate(unit);
		  
		unitAssociation.setBossId(boss.getId());
		unitAssociation.setSubOrdinateId(unit.getId());
		em.persist(unitAssociation);
		
		unitAssociations.add(unitAssociation);
		unit.setBossAssociations(unitAssociations);
		boss.setSubOrdinateAssociations(unitAssociations);		
		
		em.getTransaction().commit();		
		em.close();
		emf.close();		
	}



}
