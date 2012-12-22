package bg.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import bg.domain.Type;


@Repository
public class TypeDao {
	
	
	@PersistenceContext
    private EntityManager em;

	
	
	
	// Find all
	
	
	
	
    @Transactional(readOnly = true)
    public List<Type> findAll() {
        TypedQuery<Type> query = em.createQuery("FROM Type", Type.class);
        return query.getResultList();
    }
	

    
    
    // Delete 
    
    
    
    
    public void delete(Type type) {
    	Integer typeId = type.getId();
        if (typeId != null) {
        	delete(typeId);
        }
    }

    
    
    @Transactional
    public void delete(Integer typeId) {
        Type type = em.find(Type.class, typeId);
        if (type != null) {
        	em.remove(type);
        }
    }
	
    
    
    
    // Insert test data
	
    
    
    
	@Transactional
	public void insertTestData() {
		deleteAllTypes();
		
        Type tKU = createType("KU", "Kula");
        Type tVA = createType("VA", "Vald");
        Type tKI = createType("KI", "Kihelkond");
        Type tMA = createType("MA", "Maakond");
        Type tRI = createType("RI", "Riik");
        
        em.persist(tKU);
        em.persist(tVA);
        em.persist(tKI);
        em.persist(tMA);
        em.persist(tRI);
             
        em.flush();
        em.refresh(tKU);
        em.refresh(tVA);
        em.refresh(tKI);
        em.refresh(tMA);
        em.refresh(tRI);
        
        tRI.getSubordinateTypes().add(tMA);
        tMA.getSubordinateTypes().add(tKI);
        tKI.getSubordinateTypes().add(tVA);
        tVA.getSubordinateTypes().add(tKU);
	}
	
	
	private void deleteAllTypes() {
		em.createQuery("DELETE FROM TypeSubordinate").executeUpdate(); 
        em.flush();

		em.createQuery("DELETE FROM Type").executeUpdate();
        em.flush();

	}
	
	

	private Type createType(String code, String name) {
		Type type = new Type();
		
		type.setCode(code);
		type.setName(name);
		
		return type;
	}

}
