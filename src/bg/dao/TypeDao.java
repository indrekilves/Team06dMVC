package bg.dao;

import java.util.ArrayList;
import java.util.List;

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
	
	
	
	@PersistenceContext
    private EntityManager em;

	

	
	public TypeDao(){}
	
	
    @Transactional
    public void store(Type type) {
        em.persist(type);
        //em.merge(type);
    }
	
    
    
    
    
    
	// Find all
	
	
	
	
    @Transactional(readOnly = true)
    public List<Type> findAll() {
        TypedQuery<Type> query = em.createQuery("FROM Type WHERE opened <= NOW() AND closed >= NOW()", Type.class);
        return query.getResultList();
    }



	


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
		boss.setSubOrdinateAssociation(associations);
		
		
		em.getTransaction().commit();		
		em.close();
		emf.close();
	}

}
