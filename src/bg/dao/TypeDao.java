package bg.dao;

import java.util.ArrayList;
import java.util.List;


import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.BaseEntity;
import bg.domain.Type;
import bg.domain.TypeAssociation;
import bg.service.GenericService;


@Repository
public class TypeDao {
	
	
	
	
	// Properties 
	
	
	
	
	@PersistenceContext
    private EntityManager em;

	@Resource
	private TypeAssociationDao typeAssociationDao;
	

	
	
	// Constructor
	
	
	
	
	public TypeDao(){}
	
	
	
	
	// Save
	
	
	
    @Transactional
    public Type save(Type type) {
        if (type.getId() == null) 
        {
           em.persist(type);
           return type;
        } 
        else 
        {
        	return merge(type);
        	
        }

    }
	
	
    /**
     * Need to update only the data that can changed on the form
     */
    private Type merge(Type newType) {
    	Type oldType = getTypeById(newType.getId());
    	
    	oldType.setCode(newType.getCode());
    	oldType.setName(newType.getName());
    	oldType.setComment(newType.getComment());
    	oldType.setFromDate(newType.getFromDate());
    	oldType.setToDate(newType.getToDate());
    	
    	return em.merge(oldType);
	}

    
    
    
    
	// Find all
	
	




	@Transactional(readOnly = true)
    public List<Type> getAllTypes() {
        TypedQuery<Type> query = em.createQuery("FROM Type WHERE opened <= NOW() AND closed >= NOW()", Type.class);
        return query.getResultList();
    }


    
    
    // Find one

    
    
    
    @Transactional(readOnly = true)
	public Type getTypeById(Integer id) {
    	if (id == null) return null;

		// No need to check for dates
        return em.find(Type.class, id);
	}

    
    
    
    public Type getTypeWithAssociationById(Integer id) {
    	if (id == null) return null;
    	
    	Type type = getTypeById(id);
    	if (type == null) return null;
    	
    	
    	// Boss
    	Type boss = getBossById(type.getId());
    	if (boss != null){
    		type.setBoss(boss);
    	}
   	
    	
    	// Subordinate
    	List<Type> subOrdinates = getSubOrdinatesById(type.getId());
    	if (subOrdinates != null && !subOrdinates.isEmpty()){
    		type.setSubOrdinates(subOrdinates);
    	}
    	
    	return type;
	}

    


	public Type getBossById(Integer id) {
    	if (id == null) return null;
    	
    	List <TypeAssociation> bossAssociations = typeAssociationDao.getBossAssociationsById(id);
    	if (bossAssociations == null || bossAssociations.isEmpty()) return null;

    	Type boss = null;    	
    	for (TypeAssociation bossAssociation : bossAssociations) {
    		boss = bossAssociation.getBoss();
    		
    		if (boss != null){
    			break; // get first real boss - there should be only one though
    		}
		}    	
    	
		return boss;
	}


	
    public List<Type> getSubOrdinatesById(Integer id) {
    	if (id == null) return null;
    	
    	List <TypeAssociation> subOrdinateAssociations = typeAssociationDao.getSubOrdinateAssociationsById(id);
    	if (subOrdinateAssociations == null || subOrdinateAssociations.isEmpty()) return null;

    	List<Type> subOrdinates = new ArrayList<Type>();    	
    	for (TypeAssociation subOrdinateAssociation : subOrdinateAssociations) {
    		Type subOrdinate = subOrdinateAssociation.getSubOrdinate();
    		if (subOrdinate != null){
    			subOrdinates.add(subOrdinate);
    		}
		}    	
    	
		return subOrdinates;
	}

	

	// Find all possible bosses

	
	
	
	public List<Type> getAllPossibleBossTypesByType(Type type) {
		List<Type> possibleTypes = new ArrayList<Type>();
		List<Type> allTypes = getAllTypes();
		
		if (!allTypes.isEmpty()) {
			for (Type validateType : allTypes) {
				if (isTypeValidForBoss(validateType, type)){
					possibleTypes.add(validateType);
				}
			}
		}
		
		return possibleTypes;
	}




	private boolean isTypeValidForBoss(Type validateType, Type type) {
		// can't be itself
		if (type.equals(validateType)){
			return false;
		}
		
		// can't be subordinate
		List <Type> subOrdinates = type.getSubOrdinates();
		if (isTypeASubordinate(validateType, subOrdinates)) {
			return false;
		}
			
		return true;
	}




	private boolean isTypeASubordinate(Type validateType, List<Type> subOrdinates) {
		if (subOrdinates != null && !subOrdinates.isEmpty()) 
		{
			for (Type subOrdinate : subOrdinates) 
			{
				if (validateType.equals(subOrdinate))
				{
					return true;
				} 
				else 
				{
					// is subOrdinate of subOrdinate
					List<Type> subOrdinateSubOrdinates = getSubOrdinatesById(subOrdinate.getId());
					if (subOrdinateSubOrdinates != null) 
					{
						if (isTypeASubordinate(validateType, subOrdinateSubOrdinates))
						{
							return true;
						}
					}					 
				}
			}			
		}
		
		return false;
	}


	
	
	// Find all possible subOrdinates
	
	


	public List<Type> getAllPossibleSubordinatesByType(Type type) {
		List<Type> possibleTypes = new ArrayList<Type>();
		List<Type> allTypes = getAllTypes();
		
		if (!allTypes.isEmpty()){
			for (Type validateType : allTypes) {
				if (isTypeValidForSubordinate(validateType, type)){
					possibleTypes.add(validateType);
				}
			}		
		}
		
		return possibleTypes;	
	}


	private boolean isTypeValidForSubordinate(Type validateType, Type type) {
		// can't be itself
		if (validateType.getId() == type.getId()){
			return false;
		}
		

		// can't be subordinate
		List<Type> subOrdinates = type.getSubOrdinates();
		boolean isSubordinate = isTypeASubordinate(validateType, subOrdinates);
		if (isSubordinate) {
			return false;
		}
		
		// can't be boss
		Type boss = type.getBoss();
		boolean isBoss = isTypeABoss(validateType, boss);
		if (isBoss) {
			return false;
		}
		
		
		return true;	
	}


	private boolean isTypeABoss(Type validateType, Type boss) {
		if (boss != null)
		{						
			if (validateType.getId() == boss.getId())
			{
				return true;
			} 
			else 
			{
				// is bosses boss				
				Type bossesBoss = getBossById(boss.getId());
				if (bossesBoss != null) 
				{
					if (isTypeABoss(validateType, bossesBoss))
					{
						return true;
					}
				}
			}
		}

		return false;
	}
	

	
	
	// Integrity checking 




	@Transactional (readOnly=true)
	public boolean isCodeExisting(String code) 
	{
		if (code == null || code.length() < 1) 
		{
			return false;
		}

		
        TypedQuery<Type> query = em.createQuery("FROM Type WHERE UPPER(code) = :code", Type.class);
        query.setParameter("code", code.toUpperCase());    
        List <Type> existingTypes = query.getResultList();
        
        if (existingTypes.isEmpty())
        {
        	return false;
        }
        else
        {
        	return true;
        }
   	}


	

	// Remove / close type

	
	
	
	public void closeType(Type type) {
		if (type == null) return;
		
		// Close associations
		typeAssociationDao.closeAllTypeAssociationsById(type.getId());
		
		// Close type
		EntityManagerFactory emf = GenericService.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		type.setClosed(BaseEntity.getToday());
		type.setClosedBy(BaseEntity.getLoggedUserName());
		em.merge(type);
		
		em.getTransaction().commit();
		em.close();
		emf.close();
	}



	// Get typeId

	
	public Integer getTypeIdByCode(String code) {
		if (code == null) return null;
		
		String sql = 	"SELECT id " +
						"FROM 	Type " +
						"WHERE 	UPPER(code)  = :code " +
						"  AND 	opened 		<= NOW() " +
						"  AND 	closed		>= NOW() " ;
		
        Query query = em.createQuery(sql);
        query.setParameter("code", code.toUpperCase());
        Integer typeID = (Integer) query.getResultList().get(0);
		
        return typeID;
	}


	
	
	// Get top level types

	
	

	public List<Type> getTopLevelTypes() {
		List <Type> allTypes = getAllTypes();
		List <Type> topLevelTypes = new ArrayList<Type>();

		for (Type type : allTypes) {
			if (type != null){
				if (hasBossById(type.getId()) == false){
					topLevelTypes.add(type);
				}
			}
		}
		
		
		return topLevelTypes;
	}


	private boolean hasBossById(Integer id) {
		Type boss = getBossById(id); 
    	return boss != null;
	}


	

	// Get all nested subOrdinates
	
	
	

	public List<Type> getAllNestedSubOrdinatesById(Integer id) {
		if (id == null) return null;
		Type type = getTypeWithAssociationById(id);
		if (type == null) return null;
		
	    List<Type> allSubOrdinates	= new ArrayList<Type>();
	    List<Type> directSubOrdinates = type.getSubOrdinates();
	    
	    if (directSubOrdinates == null || directSubOrdinates.isEmpty()) return null;
	    
	    for (Type subType : directSubOrdinates) {
	    	if (subType != null){

	    		List<Type> subTypeSubOrdinates = getAllNestedSubOrdinatesById(subType.getId());
		    	if (subTypeSubOrdinates != null && !subTypeSubOrdinates.isEmpty()){
		    		subType.setSubOrdinates(subTypeSubOrdinates);
		    	}
	    		
	    		allSubOrdinates.add(subType);
	    	}
	    	
		}
	    
		return allSubOrdinates;
	}





}
