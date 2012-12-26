package bg.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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
	
	
	
	
	// Store
	
	
	
    @Transactional
    public void save(Type type) {
        if (type.getId() == null) 
        {
            em.persist(type);
        } 
        else 
        {
        	merge(type);
        }

    }
	
	
    /**
     * Need to update only the data that can changed on the form
     */
    private void merge(Type newType) {
    	Type oldType = getTypeById(newType.getId());
    	
    	oldType.setCode(newType.getCode());
    	oldType.setName(newType.getName());
    	oldType.setComment(newType.getComment());
    	oldType.setFromDate(newType.getFromDate());
    	oldType.setToDate(newType.getToDate());
    	
    	em.merge(oldType);
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

    


	private Type getBossById(Integer id) {
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


	
    private List<Type> getSubOrdinatesById(Integer id) {
    	if (id == null) return null;
    	
    	List <TypeAssociation> subOrdinateAssociations = typeAssociationDao.getSubOrdinateAssociations(id);
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

    
    
    
    // Add boss


	public void addBossToType(Type boss, Type type) {
		if (boss == null || type == null) return; 
		
		EntityManagerFactory 	emf = GenericService.getEntityManagerFactory();
		EntityManager 			em 	= emf.createEntityManager();
		em.getTransaction().begin();
		
		List<TypeAssociation> associations = new ArrayList<TypeAssociation>();		
		TypeAssociation association = new TypeAssociation();
		association.setBoss(boss);
		association.setSubOrdinate(type);
		  
		association.setBossId(boss.getId());
		association.setSubOrdinateId(type.getId());
		em.persist(association);
		
		associations.add(association);
		type.setBossAssociations(associations);
		boss.setSubOrdinateAssociations(associations);		
		
		em.getTransaction().commit();		
		em.close();
		emf.close();
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
        
        
        if (query.getSingleResult() != null)
        {
        	return true;
        }
        else
        {
        	return false;
        }
   	}








}
