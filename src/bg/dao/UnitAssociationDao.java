package bg.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import bg.domain.UnitAssociation;

@Repository
public class UnitAssociationDao {

	
	
	
	// Properties
	

	@PersistenceContext
    private EntityManager em;
	
	@Resource
	private UnitDao unitDao;
	
	
	
	
	
	// Constructors
	
	
	
	
	public UnitAssociationDao(){}
	
	
	
	
	// Find all 
	

	
	
    @Transactional(readOnly = true)
	public List<UnitAssociation> getAllUnitAccociations() {
		TypedQuery<UnitAssociation> query = em.createQuery("FROM UnitAssociation WHERE opened <= NOW() AND closed >= NOW()", UnitAssociation.class);
    	return query.getResultList();
	}

}
