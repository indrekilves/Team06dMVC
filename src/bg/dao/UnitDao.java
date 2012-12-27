package bg.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.BaseEntity;
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


	
	
	// Save 
	

	
	
	public Unit save(Unit unit) {
		EntityManagerFactory 	emf = GenericService.getEntityManagerFactory();
		EntityManager 			em 	= emf.createEntityManager();
		em.getTransaction().begin();
		
        if (unit.getId() == null) 
        {
           em.persist(unit);
        } 
        else 
        {
        	unit = merge(em, unit);
        }
        		
		em.getTransaction().commit();		
		em.close();
		emf.close();	
		
		return unit;
	}

	
    	
    /**
     * Need to update only the data that can changed on the form
     */
    private Unit merge(EntityManager em, Unit newUnit) {
    	Unit oldUnit = getUnitById(newUnit.getId());
    	
    	oldUnit.setCode(newUnit.getCode());
    	oldUnit.setName(newUnit.getName());
    	oldUnit.setComment(newUnit.getComment());
    	oldUnit.setFromDate(newUnit.getFromDate());
    	oldUnit.setToDate(newUnit.getToDate());
    	
    	return em.merge(oldUnit);
	}

	
    
	
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
		if (unit != null && unit.getTypeId() != null){
			Type type = typeDao.getTypeById(unit.getTypeId());
			if (type == null) return null;
			
			unit.setType(type);
		}
		
		return unit;
	}


	
	
	// find one
	

	public Unit getUnitWithAssociationById(Integer id) {
    	if (id == null) return null;
    	
    	Unit unit = getUnitById(id);
    	if (unit == null) return null;
    	
    	
    	// Boss
    	Unit boss = getBossById(unit.getId());
    	if (boss != null){
    		unit.setBoss(boss);
    	}
   	
    	
    	// Subordinate
    	List<Unit> subOrdinates = getSubOrdinatesById(unit.getId());
    	if (subOrdinates != null && !subOrdinates.isEmpty()){
    		unit.setSubOrdinates(subOrdinates);
    	}
    	
    	return unit;
	}
	
	
	
	
	@Transactional(readOnly=true)
	public Unit getUnitById(Integer id) {
    	if (id == null) return null;
        Unit unit = em.find(Unit.class, id);
        
        if (unit != null){
        	unit = setType(unit);
        }
        
        return unit;
	}




	public Unit getBossById(Integer id) {
    	if (id == null) return null;
    	
    	List <UnitAssociation> bossAssociations = unitAssociationDao.getBossAssociationsById(id);
    	if (bossAssociations == null || bossAssociations.isEmpty()) return null;

    	Unit boss = null;    	
    	for (UnitAssociation bossAssociation : bossAssociations) {
    		boss = bossAssociation.getBoss();
    		
    		if (boss != null){
    			break; // get first real boss - there should be only one though
    		}
		}    	
    	
		return boss;	
	}




	public List<Unit> getSubOrdinatesById(Integer id) {
    	if (id == null) return null;
    	
    	List <UnitAssociation> subOrdinateAssociations = unitAssociationDao.getSubOrdinateAssociationsById(id);
    	if (subOrdinateAssociations == null || subOrdinateAssociations.isEmpty()) return null;

    	List<Unit> subOrdinates = new ArrayList<Unit>();    	
    	for (UnitAssociation subOrdinateAssociation : subOrdinateAssociations) {
    		Unit subOrdinate = subOrdinateAssociation.getSubOrdinate();
    		if (subOrdinate != null){
    			subOrdinates.add(subOrdinate);
    		}
		}    	
    	
		return subOrdinates;
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




	// Find all possible bosses

	
	
	
	public List<Unit> getAllPossibleBossUnitsByUnit(Unit unit) {
		List<Unit> possibleUnits = new ArrayList<Unit>();
		List<Unit> allUnits = getAllUnits();
		
		if (!allUnits.isEmpty()) {
			for (Unit validateUnit : allUnits) {
				if (isUnitValidForBoss(validateUnit, unit)){
					possibleUnits.add(validateUnit);
				}
			}
		}
		
		return possibleUnits;
	}




	private boolean isUnitValidForBoss(Unit validateUnit, Unit unit) {
		// can't be itself
		if (unit.equals(validateUnit)){
			return false;
		}
		
		// can't be subordinate
		List <Unit> subOrdinates = unit.getSubOrdinates();
		if (isTypeASubordinate(validateUnit, subOrdinates)) {
			return false;
		}
			
		return true;
	}




	private boolean isTypeASubordinate(Unit validateUnit, List<Unit> subOrdinates) {
		if (subOrdinates != null && !subOrdinates.isEmpty()) 
		{
			for (Unit subOrdinate : subOrdinates) 
			{
				if (validateUnit.equals(subOrdinate))
				{
					return true;
				} 
				else 
				{
					// is subOrdinate of subOrdinate
					List<Unit> subOrdinateSubOrdinates = getSubOrdinatesById(subOrdinate.getId());
					if (subOrdinateSubOrdinates != null) 
					{
						if (isTypeASubordinate(validateUnit, subOrdinateSubOrdinates))
						{
							return true;
						}
					}					 
				}
			}			
		}
		
		return false;
	}



	
	// Integrity checks
	
	
	

	public boolean isCodeExisting(String code) {
		if (code == null || code.length() < 1) 
		{
			return false;
		}

		
        TypedQuery<Unit> query = em.createQuery("FROM Unit WHERE UPPER(code) = :code", Unit.class);
        query.setParameter("code", code.toUpperCase());    
        List <Unit> existingUnits = query.getResultList();
        
        if (existingUnits.isEmpty())
        {
        	return false;
        }
        else
        {
        	return true;
        }	
    }

	
	

	// Find all possible subOrdinates

	
	

	public List<Unit> getAllPossibleSubordinatesByUnit(Unit unit) {
		List<Unit> possibleUnits = new ArrayList<Unit>();
		List<Unit> allUnits = getAllUnits();
		
		if (!allUnits.isEmpty()){
			for (Unit validateUnit : allUnits) {
				if (isUnitValidForSubordinate(validateUnit, unit)){
					possibleUnits.add(validateUnit);
				}
			}		
		}
		
		return possibleUnits;	
	}




	private boolean isUnitValidForSubordinate(Unit validateUnit, Unit unit) {
		// can't be itself
		if (validateUnit.getId() == unit.getId()){
			return false;
		}
		

		// can't be subordinate
		List<Unit> subOrdinates = unit.getSubOrdinates();
		boolean isSubordinate = isTypeASubordinate(validateUnit, subOrdinates);
		if (isSubordinate) {
			return false;
		}
		
		// can't be boss
		Unit boss = unit.getBoss();
		boolean isBoss = isUnitABoss(validateUnit, boss);
		if (isBoss) {
			return false;
		}
		
		
		return true;	
	}




	private boolean isUnitABoss(Unit validateUnit, Unit boss) {
		if (boss != null)
		{						
			if (validateUnit.getId() == boss.getId())
			{
				return true;
			} 
			else 
			{
				// is bosses boss				
				Unit bossesBoss = getBossById(boss.getId());
				if (bossesBoss != null) 
				{
					if (isUnitABoss(validateUnit, bossesBoss))
					{
						return true;
					}
				}
			}
		}

		return false;
	}


	
	
	// Close unit

	
	

	public void closeUnit(Unit unit) {
		if (unit == null) return;
		
		// Close associations
		unitAssociationDao.closeAllTypeAssociationsById(unit.getId());
		
		// Close type
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		unit.setClosed(BaseEntity.getToday());
		unit.setClosedBy(BaseEntity.getLoggedUserName());
		em.merge(unit);
		
		em.getTransaction().commit();
		em.close();
		emf.close();
	}











}
